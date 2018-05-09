package com.defaultlogic.model;

import java.util.LinkedList;
import java.util.List;

public class DefaultTheory {

  private final List<Formula> facts;

  private final List<DefaultRule> defaultRules;

  DefaultTheory(List<Formula> facts, List<DefaultRule> defaultRules) {
    this.facts = facts;
    this.defaultRules = defaultRules;
  }

  public List<Formula> getFacts() {
    return facts;
  }

  public List<DefaultRule> getDefaultRules() {
    return defaultRules;
  }

  public static DefaultTheoryBuilder builder() {
    return new DefaultTheoryBuilder();
  }

  public static class DefaultTheoryBuilder {
    private List<Formula> facts;

    private List<DefaultRule> defaultRules;

    public DefaultTheoryBuilder() {
      this.facts = new LinkedList<>();
      this.defaultRules = new LinkedList<>();
    }

    public DefaultTheoryBuilder withFact(Formula fact) {
      facts.add(fact);
      return this;
    }

    public DefaultTheoryBuilder withRule(DefaultRule rule) {
      defaultRules.add(rule);
      return this;
    }

    public DefaultTheory build() {
      return new DefaultTheory(facts, defaultRules);
    }
  }

}
