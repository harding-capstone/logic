package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnEnactorFactory {

  /**
   * Creates the proper TurnEnactor for a given turn
   */
  TurnEnactor getEnactor(Turn turn);
}
