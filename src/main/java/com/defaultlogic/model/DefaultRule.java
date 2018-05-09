package com.defaultlogic.model;

import java.util.LinkedList;
import java.util.List;

public class DefaultRule {

  private final Formula prerequisite;

  private final List<Formula> justifications;

  private final Formula conclusion;

  DefaultRule(Formula prerequisite, Formula conclusion, List<Formula> justifications) {
    this.prerequisite = prerequisite;
    this.conclusion = conclusion;
    this.justifications = justifications;
  }

  public Formula getPrerequisite() {
    return prerequisite;
  }

  public List<Formula> getJustifications() {
    return justifications;
  }

  public Formula getConclusion() {
    return conclusion;
  }

  public static DefaultRuleBuilder builder() {
    return new DefaultRuleBuilder();
  }

  public static class DefaultRuleBuilder {

    private Formula prerequisite;

    private List<Formula> justifications;

    private Formula conclusion;

    public DefaultRuleBuilder() {
      this.justifications = new LinkedList<>();
    }

    public DefaultRuleBuilder withPrerequisite(Formula prerequisite) {
      this.prerequisite = prerequisite;
      return this;
    }

    public DefaultRuleBuilder withConclusion(Formula conclusion) {
      this.conclusion = conclusion;
      return this;
    }

    public DefaultRuleBuilder withJustification(Formula justification) {
      justifications.add(justification);
      return this;
    }

    public DefaultRule build() {
      return new DefaultRule(prerequisite, conclusion, justifications);
    }

  }

}
