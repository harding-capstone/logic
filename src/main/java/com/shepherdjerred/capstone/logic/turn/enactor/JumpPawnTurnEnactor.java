package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum JumpPawnTurnEnactor implements TurnEnactor {
  INSTANCE;

  @Override
  public Match enactTurn(Turn turn, Match match) {
    // TODO
    return match;
  }
}
