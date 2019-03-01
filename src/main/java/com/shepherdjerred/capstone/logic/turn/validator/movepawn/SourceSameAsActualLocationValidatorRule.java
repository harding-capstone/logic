package com.shepherdjerred.capstone.logic.turn.validator.movepawn;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class SourceSameAsActualLocationValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    if (turn.getSource().equals(match.getBoard().getPawnLocation(turn.getCauser()))) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.SOURCE_DIFFERENT_FROM_ACTUAL_LOCATION);
    }
  }
}
