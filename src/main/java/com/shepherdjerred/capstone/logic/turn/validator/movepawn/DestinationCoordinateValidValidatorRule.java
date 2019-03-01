package com.shepherdjerred.capstone.logic.turn.validator.movepawn;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class DestinationCoordinateValidValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var board = match.getBoard();
    if (board.isCoordinateInvalid(turn.getDestination())) {
      return new TurnValidationResult(ErrorMessage.DESTINATION_COORDINATE_INVALID);
    } else {
      return new TurnValidationResult();
    }
  }
}
