package com.shepherdjerred.capstone.logic.turn.exception;

import com.shepherdjerred.capstone.logic.turn.Turn;

public class InvalidTurnException extends RuntimeException {

  public InvalidTurnException(Turn turn) {
    super(turn.toString());
  }

}
