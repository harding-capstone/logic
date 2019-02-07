package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.JumpPawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum JumpPawnTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param board The board state
   * @return The board state transformed by the turn
   */
  @Override
  public Board enactTurn(Turn turn, Board board) {
    if (turn instanceof JumpPawnTurn) {
      return enactJumpPawnTurn((JumpPawnTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a JumpPawnTurn " + turn);
    }
  }

  private Board enactJumpPawnTurn(JumpPawnTurn turn, Board board) {
    return board.movePawn(turn.getCauser(), turn.getDestination());
  }
}
