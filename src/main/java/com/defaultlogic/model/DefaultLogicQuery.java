package com.defaultlogic.model;

public class DefaultLogicQuery {

  private final DefaultTheory defaultTheory;

  private final Formula query;

  public DefaultLogicQuery(DefaultTheory defaultTheory, Formula query) {
    this.defaultTheory = defaultTheory;
    this.query = query;
  }

  public DefaultTheory getDefaultTheory() {
    return defaultTheory;
  }

  public Formula getQuery() {
    return query;
  }
}
