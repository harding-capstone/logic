package com.shepherdjerred.capstone.logic.turn.validators.movepawn;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.ValidatorRule;
import lombok.ToString;

@ToString
public class DestinationBoardCellTypeIsPawnValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var destination = turn.getDestination();

    if (match.getBoard().isCoordinateInvalid(destination)) {
      return new TurnValidationResult(true);
    }

    if (match.getBoard().isPawnBoardCell(destination)) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.DESTINATION_CELL_TYPE_NOT_PAWN);
    }
  }
}
