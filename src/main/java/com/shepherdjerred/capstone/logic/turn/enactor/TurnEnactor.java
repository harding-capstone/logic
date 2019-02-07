package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnEnactor {

  /**
   * Takes the steps to transform a given match state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param match The match state
   * @return The match state transformed by the turn
   */
  Match enactTurn(Turn turn, Match match);
}
