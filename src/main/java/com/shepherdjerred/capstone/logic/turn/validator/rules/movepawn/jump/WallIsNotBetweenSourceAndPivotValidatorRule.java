package com.shepherdjerred.capstone.logic.turn.validator.rules.movepawn.jump;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.JumpPawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.rules.ValidatorRule;

public class WallIsNotBetweenSourceAndPivotValidatorRule implements ValidatorRule<JumpPawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, JumpPawnTurn turn) {
    return null;
  }
}
