package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnValidator {
  boolean isTurnValid(Turn turn, Match match);
}
