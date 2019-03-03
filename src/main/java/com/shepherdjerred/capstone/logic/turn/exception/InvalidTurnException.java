package com.shepherdjerred.capstone.logic.turn.exception;

import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;

public class InvalidTurnException extends RuntimeException {

  public InvalidTurnException(Turn turn) {
    super(turn.toString());
  }

  public InvalidTurnException(Turn turn, TurnValidationResult TurnValidationResult) {
    super(turn.toString() + " " + TurnValidationResult.toString());
  }

}
