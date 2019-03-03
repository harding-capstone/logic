package com.shepherdjerred.capstone.logic.turn.validators.rules;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;

public interface ValidatorRule<T extends Turn> {

  TurnValidationResult validate(Match match, T turn);
}
