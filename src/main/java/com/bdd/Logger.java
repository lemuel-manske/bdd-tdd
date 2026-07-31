package com.bdd;

public class Logger {

  private static final Logger THIS = new Logger();

  public static Logger getInstance() {
    return THIS;
  }

  public String getLastMessage() {
    throw new UnsupportedOperationException("Ainda não implementado");
  }
}
