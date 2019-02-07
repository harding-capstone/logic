package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum JumpPawnTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public TurnValidationResult isTurnValid(Turn turn, Match match) {
    // TODO
    return new TurnValidationResult(false);
  }
}
