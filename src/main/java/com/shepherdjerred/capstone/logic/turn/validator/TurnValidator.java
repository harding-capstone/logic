package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.MatchState;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnValidator {

  boolean isTurnValid(Turn turn, MatchState matchState);
}
