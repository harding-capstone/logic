package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum MovePawnTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param match The match state
   * @return The match state transformed by the turn
   */
  @Override
  public Match enactTurn(Turn turn, Match match) {
    if (turn instanceof MovePawnTurn) {
      return enactMovePawnTurn((MovePawnTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  // TODO check for victory
  // Also this builder is terrible but ¯\_(ツ)_/¯
  private Match enactMovePawnTurn(MovePawnTurn turn, Match match) {
    var board = match.getBoard();
    var newBoard = board.movePawn(turn.getCauser(), turn.getDestination());
    return match; // TODO
  }
}
