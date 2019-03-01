package com.shepherdjerred.capstone.logic.turn.validator.placewall;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class WallPieceLocationVertexIsFreeValidatorRule implements ValidatorRule<PlaceWallTurn> {

  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
    var wallLocation = turn.getLocation();
    var vertex = wallLocation.getVertex();
    var board = match.getBoard();
    if (board.hasWall(vertex)) {
      return new TurnValidationResult(ErrorMessage.VERTEX_NOT_FREE);
    } else {
      return new TurnValidationResult();
    }
  }
}
