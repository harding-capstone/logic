package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.MatchState;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnEnactor {

  /**
   * Takes the steps to transform a given matchState state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the boardState
   * @param matchState The initial matchState state
   * @return The initial matchState state transformed by the turn
   */
  MatchState enactTurn(Turn turn, MatchState matchState);
}
