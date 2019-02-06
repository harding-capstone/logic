package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnValidatorFactory {

  TurnValidator getValidator(Turn turn);
}
