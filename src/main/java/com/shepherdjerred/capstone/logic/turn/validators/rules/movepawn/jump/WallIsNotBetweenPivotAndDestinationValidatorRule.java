package com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.JumpPawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;

public class WallIsNotBetweenPivotAndDestinationValidatorRule implements ValidatorRule<JumpPawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, JumpPawnTurn turn) {
    return null;
  }
}
