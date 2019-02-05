package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum TurnValidatorFactory {
  INSTANCE;
  public TurnValidator getValidator(Turn turn) {
    if (turn instanceof MovePawnTurn) {
      return MovePawnTurnValidator.INSTANCE;
    } else if (turn instanceof PlaceWallTurn) {
      return PlaceWallTurnValidator.INSTANCE;
    } else {
      throw new IllegalStateException("Unknown turn " + turn);
    }
  }
}
