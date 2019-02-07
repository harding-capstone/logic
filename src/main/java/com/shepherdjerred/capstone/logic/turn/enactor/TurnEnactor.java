package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnEnactor {

  /**
   * Takes the steps to transform a given match state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param board The board state
   * @return The board state transformed by the turn
   */
  Board enactTurn(Turn turn, Board board);
}
