package com.defaultlogic.check;

import com.defaultlogic.model.DefaultLogicQuery;
import com.defaultlogic.model.DefaultRule;
import com.defaultlogic.model.Formula;

import java.util.LinkedList;
import java.util.List;

public class ModelChecker {

  public ModelCheckResult check(DefaultLogicQuery logicQuery) {
    List<DefaultRule> reducatedRules = new LinkedList<>();

    for (DefaultRule rule : logicQuery.getDefaultTheory().getDefaultRules()) {
      if (!isPrerequisiteAndJustificationsContradicting(rule)) {
        reducatedRules.add(DefaultRule.builder()
            .withPrerequisite(rule.getPrerequisite())
            .withConclusion(rule.getConclusion())
            .build()
        );
      }
    }

    List<Formula> extendedFacts = new LinkedList<>();
    extendedFacts.addAll(logicQuery.getDefaultTheory().getFacts());

    for (DefaultRule reducatedRule : reducatedRules) {
      for (Formula fact : logicQuery.getDefaultTheory().getFacts()) {
        if (fact.shallowEquals(reducatedRule.getPrerequisite())) {
          extendedFacts.add(
              new Formula(reducatedRule.getConclusion().getSymbol(),
                  fact.getSubject(),
                  reducatedRule.getConclusion().isNegated()
              )
          );
        }
      }
    }

    for (int i = 0; i < extendedFacts.size(); ++i) {
      for (int j = i + 1; j < extendedFacts.size(); ++j) {
        if (extendedFacts.get(j).isContradicting(extendedFacts.get(i))) {
          return new ModelCheckResult(ModelCheckResult.ResultType.ERROR, "The extended default theory has contradicting facts!");
        }
      }
    }

    if (extendedFacts.contains(logicQuery.getQuery())) {
      return new ModelCheckResult(ModelCheckResult.ResultType.MATCH, "The queried formula is in the extended default theory!");
    } else {
      return new ModelCheckResult(ModelCheckResult.ResultType.NO_MATCH, "The queried formula is not in the extended default theory!");
    }
  }

  private boolean isPrerequisiteAndJustificationsContradicting(DefaultRule rule) {
    for (Formula justification : rule.getJustifications()) {
      if (rule.getPrerequisite().isContradicting(justification)) {
        return true;
      }
    }

    return false;
  }

}
