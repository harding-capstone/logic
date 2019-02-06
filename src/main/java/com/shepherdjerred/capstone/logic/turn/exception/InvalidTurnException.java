package com.shepherdjerred.capstone.logic.turn.exception;

import com.shepherdjerred.capstone.logic.turn.Turn;

public class InvalidTurnException extends Exception {

  public InvalidTurnException() {
  }

  public InvalidTurnException(Turn turn) {
    super(turn.toString());
  }

  public InvalidTurnException(String message) {
    super(message);
  }

  public InvalidTurnException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTurnException(Throwable cause) {
    super(cause);
  }

  public InvalidTurnException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
