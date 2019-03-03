package com.shepherdjerred.capstone.logic.turn.validators.movepawn;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.ValidatorRule;
import lombok.ToString;

@ToString
public class TurnSourceIsSameAsActualLocationValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    if (turn.getSource().equals(match.getBoard().getPawnLocation(turn.getCauser()))) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.SOURCE_DIFFERENT_FROM_ACTUAL_LOCATION);
    }
  }
}
