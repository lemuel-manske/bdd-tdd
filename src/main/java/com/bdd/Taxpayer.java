package com.bdd;

import java.util.List;

public class Taxpayer {

  private final String name;
  private final String documentNumber;
  private final List<Debt> debts;

  public Taxpayer(final String name, final String documentNumber, final List<Debt> debts) {
    this.name = name;
    this.documentNumber = documentNumber;
    this.debts = debts;
  }

  public String getName() {
    return name;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public boolean hasPendingDebts() {
    return !debts.isEmpty();
  }
}
