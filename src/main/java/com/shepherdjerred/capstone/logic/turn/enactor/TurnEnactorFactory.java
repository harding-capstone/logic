package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnEnactorFactory {

  TurnEnactor getEnactor(Turn turn);
}
