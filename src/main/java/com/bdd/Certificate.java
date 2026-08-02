package com.bdd;

public class Certificate {

  private final String protocolNumber;
  private final String digitalSignature;
  private final Taxpayer taxpayer;

  public Certificate(
    final String protocolNumber,
    final String digitalSignature,
    final Taxpayer taxpayer
  ) {
    this.protocolNumber = protocolNumber;
    this.digitalSignature = digitalSignature;
    this.taxpayer = taxpayer;
  }

  public String getProtocolNumber() {
    return protocolNumber;
  }

  public String getDigitalSignature() {
    return digitalSignature;
  }

  public Taxpayer getTaxpayer() {
    return taxpayer;
  }
}
