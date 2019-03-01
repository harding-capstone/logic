package com.shepherdjerred.capstone.logic.turn.validator.movepawn;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class WallBetweenIsNotSourceAndDestinationValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

    // If the distance != 2 then we can't do this check
    if (dist != 2) {
      System.out.println("Distance != 2");
      return new TurnValidationResult(ErrorMessage.NULL);
    }

    var coordinateBetween = Coordinate.calculateMidpoint(turn.getSource(), turn.getDestination());
    if (match.getBoard().hasPiece(coordinateBetween)) {
      return new TurnValidationResult(ErrorMessage.WALL_BETWEEN_SOURCE_AND_DESTINATION);
    } else {
      return new TurnValidationResult();
    }
  }
}
