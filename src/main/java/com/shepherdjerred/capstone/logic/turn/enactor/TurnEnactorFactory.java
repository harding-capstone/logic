package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum TurnEnactorFactory {
  INSTANCE;

  public TurnEnactor getEnactor(Turn turn) {
    if (turn instanceof MovePawnTurn) {
      return MovePawnTurnEnactor.INSTANCE;
    } else if (turn instanceof PlaceWallTurn) {
      return PlaceWallTurnEnactor.INSTANCE;
    } else {
      throw new IllegalStateException("Unknown turn " + turn);
    }
  }
}
