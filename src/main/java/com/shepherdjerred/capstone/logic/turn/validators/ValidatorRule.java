package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface ValidatorRule<T extends Turn> {

  TurnValidationResult validate(Match match, T turn);
}
