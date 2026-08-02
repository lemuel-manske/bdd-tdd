package com.bdd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NegativeDebtCertificateTest {

  private static final Taxpayer TAXPAYER_WITHOUT_DEBTS =
    new Taxpayer("Mary Smith", "123.456.789-00", List.of());

  private CertificateService certificateService;

  @BeforeEach
  void setUp() {
    certificateService = new CertificateService();
  }

  @Test
  void shouldIssueNegativeDebtCertificateToTaxpayerWithoutDebts() { // Scenario 1
    Certificate first = certificateService.issue(TAXPAYER_WITHOUT_DEBTS);
    Certificate second = certificateService.issue(TAXPAYER_WITHOUT_DEBTS);

    assertAll(
      () -> assertNotNull(first),
      () -> assertNotNull(first.getProtocolNumber()),
      () -> assertFalse(first.getProtocolNumber().isBlank()),
      () -> assertNotNull(first.getDigitalSignature()),
      () -> assertFalse(first.getDigitalSignature().isBlank()),
      () -> assertTrue(certificateService.isRegistered(first.getProtocolNumber())),
      () -> assertFalse(first.getProtocolNumber().equals(second.getProtocolNumber()))
    );
  }

  @Test
  void shouldRecognizeCertificateIssuedByTheSystemAsAuthentic() { // Scenario 2
    Certificate issued = certificateService.issue(TAXPAYER_WITHOUT_DEBTS);
    Certificate presented = new Certificate(
      issued.getProtocolNumber(),
      issued.getDigitalSignature(),
      issued.getTaxpayer()
    );

    ValidationResult result = certificateService.validate(
      issued.getProtocolNumber(),
      presented,
      "interested-party"
    );

    assertAll(
      () -> assertTrue(result.isAuthentic()),
      () -> assertEquals(
        TAXPAYER_WITHOUT_DEBTS.getName(),
        result.getCertificate().getTaxpayer().getName()),
      () -> assertEquals(
        TAXPAYER_WITHOUT_DEBTS.getDocumentNumber(),
        result.getCertificate().getTaxpayer().getDocumentNumber()),
      () -> assertEquals(DebtSituation.NO_DEBTS, result.getDebtSituation())
    );
  }

  @Test
  void shouldDenyIssuanceToTaxpayerWithPendingDebts() { // Scenario 3
    Taxpayer taxpayerWithDebts = new Taxpayer(
      "John Doe",
      "987.654.321-00",
      List.of(new Debt("IPTU", 2500.00))
    );

    CertificateDeniedException exception = assertThrows(
      CertificateDeniedException.class,
      () -> certificateService.issue(taxpayerWithDebts)
    );

    assertAll(
      () -> assertTrue(exception.getMessage().contains("pending debts")),
      () -> assertEquals(0, certificateService.countIssuedCertificates())
    );
  }

  @Test
  void shouldRejectCertificateWhoseContentWasTampered() { // Scenario 4
    Certificate issued = certificateService.issue(TAXPAYER_WITHOUT_DEBTS);
    Certificate tampered = new Certificate(
      issued.getProtocolNumber(),
      issued.getDigitalSignature(),
      new Taxpayer("Mary Oliver", "123.456.789-00", List.of())
    );

    ValidationResult result = certificateService.validate(
      issued.getProtocolNumber(),
      tampered,
      "interested-party"
    );

    assertAll(
      () -> assertFalse(result.isAuthentic()),
      () -> assertTrue(result.getReason().contains("does not match the original record"))
    );
  }

  @Test
  void shouldRejectForgedCertificateWithReusedProtocolAndRegisterFraudAttempt() { // Scenario 5
    Certificate issuedToTaxpayerA = certificateService.issue(TAXPAYER_WITHOUT_DEBTS);
    Certificate forged = new Certificate(
      issuedToTaxpayerA.getProtocolNumber(),
      issuedToTaxpayerA.getDigitalSignature(),
      new Taxpayer("Jane Brown", "555.666.777-88", List.of())
    );

    ValidationResult result = certificateService.validate(
      issuedToTaxpayerA.getProtocolNumber(),
      forged,
      "attacker@example.com"
    );

    assertAll(
      () -> assertFalse(result.isAuthentic()),
      () -> assertNull(result.getCertificate()),
      () -> assertNull(result.getDebtSituation()),
      () -> assertEquals(1, certificateService.getFraudAttempts().size()),
      () -> assertEquals(
        "attacker@example.com",
        certificateService.getFraudAttempts().get(0).getRequester()),
      () -> assertEquals(
        issuedToTaxpayerA.getProtocolNumber(),
        certificateService.getFraudAttempts().get(0).getProtocolNumber())
    );
  }
}
