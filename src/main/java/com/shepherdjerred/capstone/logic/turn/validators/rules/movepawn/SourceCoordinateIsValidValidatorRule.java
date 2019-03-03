package com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import lombok.ToString;

@ToString
public class SourceCoordinateIsValidValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var board = match.getBoard();
    if (board.isCoordinateInvalid(turn.getSource())) {
      return new TurnValidationResult(ErrorMessage.SOURCE_COORDINATE_INVALID);
    } else {
      return new TurnValidationResult();
    }
  }
}
