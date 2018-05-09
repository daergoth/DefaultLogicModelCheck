package com.defaultlogic.model;

import java.util.Objects;

public class Formula {

  private final String symbol;

  private final String subject;

  private final boolean isNegated;

  public Formula(String symbol, String subject, boolean isNegated) {
    this.symbol = symbol;
    this.subject = subject;
    this.isNegated = isNegated;
  }

  public boolean isContradicting(Formula other) {
    return symbol.equalsIgnoreCase(other.symbol) && isNegated != other.isNegated;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getSubject() {
    return subject;
  }

  public boolean isNegated() {
    return isNegated;
  }

  public boolean shallowEquals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Formula formula = (Formula) o;
    return isNegated == formula.isNegated &&
        Objects.equals(symbol, formula.symbol);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Formula formula = (Formula) o;
    return isNegated == formula.isNegated &&
        Objects.equals(symbol, formula.symbol) &&
        Objects.equals(subject, formula.subject);
  }

  @Override
  public int hashCode() {

    return Objects.hash(symbol, subject, isNegated);
  }

  @Override
  public String toString() {
    return (isNegated ? "~" : "") + symbol + "(" + subject + ")";
  }
}
