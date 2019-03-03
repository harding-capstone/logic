package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.turn.JumpPawnDiagonalTurn;
import com.shepherdjerred.capstone.logic.turn.JumpPawnStraightTurn;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public class TurnValidatorFactory {

  public TurnValidator getValidator(Turn turn) {
    TurnValidator validator;

    if (turn instanceof PlaceWallTurn) {
      validator = new PlaceWallTurnValidator();
    } else if (turn instanceof NormalMovePawnTurn) {
      validator = new NormalMovePawnTurnValidator();
    } else if (turn instanceof JumpPawnStraightTurn) {
      validator = new JumpPawnStraightTurnValidator();
    } else if (turn instanceof JumpPawnDiagonalTurn) {
      validator = new JumpPawnDiagonalTurnValidator();
    } else {
      throw new UnsupportedOperationException();
    }

    return validator;
  }
}