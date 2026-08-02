package com.bdd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CertificateService {

  private final Map<String, Certificate> certificatesByProtocol = new LinkedHashMap<>();
  private final List<FraudAttempt> fraudAttempts = new ArrayList<>();
  private int protocolSequence;

  public Certificate issue(final Taxpayer taxpayer) {
    if (taxpayer.hasPendingDebts()) {
      throw new CertificateDeniedException(
        "Taxpayer has pending debts; negative debt certificate issuance denied");
    }
    String protocolNumber = "CND-" + (++protocolSequence);
    String content = contentOf(taxpayer);
    Certificate certificate = new Certificate(protocolNumber, sign(content), taxpayer);
    certificatesByProtocol.put(protocolNumber, certificate);
    return certificate;
  }

  public ValidationResult validate(
    final String protocolNumber,
    final Certificate presentedCertificate,
    final String requester
  ) {
    Certificate registered = certificatesByProtocol.get(protocolNumber);
    boolean matches = registered != null
      && contentOf(registered.getTaxpayer()).equals(contentOf(presentedCertificate.getTaxpayer()));

    if (!matches) {
      fraudAttempts.add(new FraudAttempt(protocolNumber, requester, LocalDateTime.now()));
      return new ValidationResult(
        false,
        null,
        null,
        "The document does not match the original record");
    }
    return new ValidationResult(true, registered, DebtSituation.NO_DEBTS, null);
  }

  public boolean isRegistered(final String protocolNumber) {
    return certificatesByProtocol.containsKey(protocolNumber);
  }

  public int countIssuedCertificates() {
    return certificatesByProtocol.size();
  }

  public List<FraudAttempt> getFraudAttempts() {
    return fraudAttempts;
  }

  private String contentOf(final Taxpayer taxpayer) {
    return taxpayer.getName() + "|" + taxpayer.getDocumentNumber();
  }

  private String sign(final String content) {
    return Integer.toHexString(content.hashCode());
  }
}
