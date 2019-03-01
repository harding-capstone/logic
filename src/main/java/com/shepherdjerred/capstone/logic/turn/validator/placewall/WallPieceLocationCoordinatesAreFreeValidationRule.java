package com.shepherdjerred.capstone.logic.turn.validator.placewall;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class WallPieceLocationCoordinatesAreFreeValidationRule implements ValidatorRule<PlaceWallTurn> {

  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
    var board = match.getBoard();
    var location = turn.getLocation();
    var firstCoordinate = location.getFirstCoordinate();
    var secondCoordinate = location.getSecondCoordinate();

    if (board.isEmpty(firstCoordinate)
        && board.isEmpty(secondCoordinate)) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_EMPTY);
    }
  }
}
