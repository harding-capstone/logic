package com.shepherdjerred.capstone.logic.exceptions;

public class IllegalTurnException extends Exception {
  public IllegalTurnException() {
  }

  public IllegalTurnException(String message) {
    super(message);
  }

  public IllegalTurnException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalTurnException(Throwable cause) {
    super(cause);
  }

  public IllegalTurnException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
