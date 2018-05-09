package com.defaultlogic.check;

public class ModelCheckResult {

  public enum ResultType {
    ERROR,
    NO_MATCH,
    MATCH
  }

  private ResultType type;

  private String reason;

  public ModelCheckResult(ResultType type, String reason) {
    this.type = type;
    this.reason = reason;
  }

  public ResultType getType() {
    return type;
  }

  public String getReason() {
    return reason;
  }
}
