package com.shepherdjerred.capstone.logic.turn.validator.placewall;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class WallPieceLocationVertexIsVertexBoardCellValidatorRule implements ValidatorRule<PlaceWallTurn> {

  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
    var location = turn.getLocation();
    var vertex = location.getVertex();
    var board = match.getBoard();
    if (board.isWallVertex(vertex)) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.VERTEX_COORDINATE_IS_NOT_VERTEX_CELL);
    }
  }
}
