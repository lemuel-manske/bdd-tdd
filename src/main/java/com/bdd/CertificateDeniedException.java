package com.bdd;

public class CertificateDeniedException extends RuntimeException {

  public CertificateDeniedException(final String message) {
    super(message);
  }
}
