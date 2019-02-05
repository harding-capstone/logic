package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum MovePawnTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public boolean isTurnValid(Turn turn, Board board) {
    if (turn instanceof MovePawnTurn) {
      return isMovePawnTurnValid((MovePawnTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  private boolean isMovePawnTurnValid(MovePawnTurn turn, Board board) {
    // TODO
    return true;
  }
}
