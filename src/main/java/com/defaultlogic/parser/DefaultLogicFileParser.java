package com.defaultlogic.parser;

import com.defaultlogic.model.DefaultLogicQuery;
import com.defaultlogic.model.DefaultRule;
import com.defaultlogic.model.DefaultTheory;
import com.defaultlogic.model.Formula;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File pattern:
 * <p>
 * M(C)
 * P(T)
 * <p>
 * M(x):R(x)/R(x)
 * P(x):M(x),~R(x)/~R(x)
 * <p>
 * R(C)
 */
public class DefaultLogicFileParser {

  private static final Pattern FORMULA_PATTERN = Pattern.compile("^(?<negSign>~?)(?<symbol>[A-Z]+)\\((?<subject>[A-Za-z,]+)\\)$");

  private static final Pattern RULE_PATTERN = Pattern.compile("^(?<prerequisite>.+):(?<justifications>.+)/(?<conclusion>.+)$");

  private enum ParseState {
    FACTS,
    RULES,
    QUERY;

    public ParseState next() {
      switch (this) {
        case FACTS:
          return RULES;
        case RULES:
          return QUERY;
        case QUERY:
          return FACTS;
        default:
          return FACTS;
      }
    }
  }

  private ParseState currentParseState = ParseState.FACTS;

  public DefaultLogicQuery parseFile(String filename) throws IOException {
    DefaultTheory.DefaultTheoryBuilder theoryBuilder = DefaultTheory.builder();
    Formula query = null;

    BufferedReader reader = new BufferedReader(new FileReader(filename));

    while (reader.ready()) {
      String line = reader.readLine();

      if (line.isEmpty()) {
        currentParseState = currentParseState.next();
        continue;
      }

      switch (currentParseState) {
        case FACTS:
          Formula fact = createFormulaFromString(line);
          if (fact != null) {
            theoryBuilder.withFact(fact);
          }
          break;
        case RULES:
          DefaultRule rule = createRuleFromString(line);
          if (rule != null) {
            theoryBuilder.withRule(rule);
          }
          break;
        case QUERY:
          query = createFormulaFromString(line);
          break;
      }

    }

    return new DefaultLogicQuery(theoryBuilder.build(), query);
  }

  private Formula createFormulaFromString(String formulaLine) {
    Matcher matcher = FORMULA_PATTERN.matcher(formulaLine);

    if (matcher.matches()) {
      String negSign = matcher.group("negSign");
      String symbol = matcher.group("symbol");
      String subject = matcher.group("subject");

      return new Formula(symbol, subject, !negSign.isEmpty());
    }

    return null;
  }

  private DefaultRule createRuleFromString(String ruleLine) {
    Matcher matcher = RULE_PATTERN.matcher(ruleLine);

    if (matcher.matches()) {
      DefaultRule.DefaultRuleBuilder ruleBuilder = DefaultRule.builder()
          .withPrerequisite(createFormulaFromString(matcher.group("prerequisite")))
          .withConclusion(createFormulaFromString(matcher.group("conclusion")));

      String[] justificationStrings = matcher.group("justifications").split(";");
      for (String justification : justificationStrings) {
        ruleBuilder.withJustification(createFormulaFromString(justification));
      }

      return ruleBuilder.build();
    }

    return null;
  }

}
