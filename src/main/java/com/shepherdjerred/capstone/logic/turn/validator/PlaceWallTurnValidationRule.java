package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;

public interface PlaceWallTurnValidationRule extends TurnValidationRule<PlaceWallTurn> {

  static PlaceWallTurnValidationRule isWallCell() {
    return (turn, match) -> {
      var board = match.getBoard();
      var firstCoordinate = turn.getFirstCoordinate();
      var secondCoordinate = turn.getSecondCoordinate();

      if (board.isWallBoardCell(firstCoordinate)
          && board.isWallBoardCell(secondCoordinate)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.NOT_WALL_CELL);
      }
    };
  }

  static PlaceWallTurnValidationRule areCoordinatesEmpty() {
    return (turn, match) -> {
      var board = match.getBoard();
      var firstCoordinate = turn.getFirstCoordinate();
      var secondCoordinate = turn.getSecondCoordinate();

      if (board.isEmpty(firstCoordinate)
          && board.isEmpty(secondCoordinate)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.DESTINATION_NOT_EMPTY);
      }
    };
  }

  static PlaceWallTurnValidationRule doesPlayerHaveWallsToPlace() {
    return (turn, match) -> {
      var player = turn.getCauser();
      if (match.getWallsLeft(player) > 0) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.NO_WALLS_TO_PLACE);
      }
    };
  }

  static PlaceWallTurnValidationRule willWallBlockPawns() {
    return (turn, match) -> {
      // TODO
      return new TurnValidationResult(false);
    };
  }

  static TurnValidationRule<PlaceWallTurn> all() {
    return isWallCell()
        .and(areCoordinatesEmpty())
        .and(doesPlayerHaveWallsToPlace())
        .and(willWallBlockPawns());
  }
}
