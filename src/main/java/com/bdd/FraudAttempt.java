package com.bdd;

import java.time.LocalDateTime;

public class FraudAttempt {

  private final String protocolNumber;
  private final String requester;
  private final LocalDateTime timestamp;

  public FraudAttempt(
    final String protocolNumber,
    final String requester,
    final LocalDateTime timestamp
  ) {
    this.protocolNumber = protocolNumber;
    this.requester = requester;
    this.timestamp = timestamp;
  }

  public String getProtocolNumber() {
    return protocolNumber;
  }

  public String getRequester() {
    return requester;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}
