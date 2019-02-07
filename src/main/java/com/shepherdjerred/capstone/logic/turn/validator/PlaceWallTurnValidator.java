package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum PlaceWallTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public TurnValidationResult isTurnValid(Turn turn, Match match) {
    if (turn instanceof PlaceWallTurn) {
      return isPlaceWallTurnValid((PlaceWallTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  public TurnValidationResult isPlaceWallTurnValid(PlaceWallTurn turn, Match match) {
    return PlaceWallTurnValidationRules.all().apply(turn, match);
  }
}
