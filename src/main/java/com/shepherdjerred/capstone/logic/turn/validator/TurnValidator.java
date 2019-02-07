package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum TurnValidator {
  INSTANCE;

  public TurnValidationResult isTurnValid(Turn turn, Match match) {
    if (turn instanceof MovePawnTurn) {
      return MovePawnTurnValidationRules.all().apply((MovePawnTurn) turn, match);
    } else if (turn instanceof PlaceWallTurn) {
      return PlaceWallTurnValidationRules.all().apply((PlaceWallTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Unknown turn type " + turn);
    }
  }
}
