package com.shepherdjerred.capstone.logic.turn.exception;

import com.shepherdjerred.capstone.logic.turn.Turn;

public class TurnOutOfOrderException extends RuntimeException {
  public TurnOutOfOrderException(Turn turn) {
    super(turn.toString());
  }
}
