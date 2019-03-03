package com.shepherdjerred.capstone.logic.turn.validators.movepawn;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.ValidatorRule;
import lombok.ToString;

@ToString
public class MoveIsDiagonalValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var source = turn.getSource();
    var destination = turn.getDestination();
    if (Coordinate.areCoordinatesDiagonal(source, destination)) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.MOVE_NOT_DIAGONAL);
    }
  }
}
