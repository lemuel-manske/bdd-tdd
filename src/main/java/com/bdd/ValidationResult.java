package com.bdd;

public class ValidationResult {

  private final boolean authentic;
  private final Certificate certificate;
  private final DebtSituation debtSituation;
  private final String reason;

  public ValidationResult(
    final boolean authentic,
    final Certificate certificate,
    final DebtSituation debtSituation,
    final String reason
  ) {
    this.authentic = authentic;
    this.certificate = certificate;
    this.debtSituation = debtSituation;
    this.reason = reason;
  }

  public boolean isAuthentic() {
    return authentic;
  }

  public Certificate getCertificate() {
    return certificate;
  }

  public DebtSituation getDebtSituation() {
    return debtSituation;
  }

  public String getReason() {
    return reason;
  }
}
