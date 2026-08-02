package com.bdd;

public class Debt {

  private final String type;
  private final double amount;

  public Debt(final String type, final double amount) {
    this.type = type;
    this.amount = amount;
  }

  public String getType() {
    return type;
  }

  public double getAmount() {
    return amount;
  }
}
