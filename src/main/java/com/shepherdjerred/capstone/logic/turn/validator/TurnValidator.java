package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnValidator {

  TurnValidationResult isTurnValid(Turn turn, Match match);
}
