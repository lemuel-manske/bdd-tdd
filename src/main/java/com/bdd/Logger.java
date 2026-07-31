package com.bdd;

public class Logger {

  private static final Logger INSTANCE = new Logger();

  private String lastMessage;

  private Logger() {
    // singleton
  }

  public static Logger getInstance() {
    return INSTANCE;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void log(final String message) {
    this.lastMessage = message;
  }
}
