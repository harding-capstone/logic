package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public final class TurnValidator {

  public TurnValidationResult isTurnValid(Turn turn, Match match) {
    if (turn instanceof MovePawnTurn) {
      var movePawnTurn = (MovePawnTurn) turn;
      var turnType = movePawnTurn.getMoveType();
      if (turnType == MoveType.NORMAL) {
        return MovePawnTurnValidationRule.normal().apply(movePawnTurn, match);
      } else if (turnType == MoveType.JUMP_STRAIGHT) {
        return MovePawnTurnValidationRule.jumpStraight().apply(movePawnTurn, match);
      } else if (turnType == MoveType.JUMP_DIAGONAL) {
        return MovePawnTurnValidationRule.jumpDiagonal().apply(movePawnTurn, match);
      } else {
        throw new IllegalArgumentException("Unknown move type " + turn);
      }
    } else if (turn instanceof PlaceWallTurn) {
      return PlaceWallTurnValidationRule.all().apply((PlaceWallTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Unknown turn type " + turn);
    }
  }
}
