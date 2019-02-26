package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;

public interface PlaceWallTurnValidationRule extends TurnValidationRule<PlaceWallTurn> {

  static PlaceWallTurnValidationRule areCoordinatesValid() {
    return (turn, match) -> {
      var board = match.getBoard();
      if (board.isCoordinateInvalid(turn.getFirstCoordinate())
          || board.isCoordinateInvalid(turn.getSecondCoordinate())) {
        return new TurnValidationResult(ErrorMessage.COORDINATE_INVALID);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static PlaceWallTurnValidationRule isWallCell() {
    return (turn, match) -> {
      var board = match.getBoard();
      var firstCoordinate = turn.getFirstCoordinate();
      var secondCoordinate = turn.getSecondCoordinate();

      if (board.isCoordinateInvalid(firstCoordinate)
          || board.isCoordinateInvalid(secondCoordinate)) {
        return new TurnValidationResult(true);
      }

      if (board.isWallBoardCell(firstCoordinate)
          && board.isWallBoardCell(secondCoordinate)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.NOT_WALL_CELL);
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
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.DESTINATION_NOT_EMPTY);
      }
    };
  }

  static PlaceWallTurnValidationRule doesPlayerHaveWallsToPlace() {
    return (turn, match) -> {
      var player = turn.getCauser();
      if (match.getWallsLeft(player) > 0) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.NO_WALLS_TO_PLACE);
      }
    };
  }

  // TODO
  static PlaceWallTurnValidationRule willWallBlockPawns() {
    return (turn, match) -> {
      return new TurnValidationResult();
    };
  }

  static TurnValidationRule<PlaceWallTurn> all() {
    return areCoordinatesValid()
        .and(isWallCell())
        .and(areCoordinatesEmpty())
        .and(doesPlayerHaveWallsToPlace())
        .and(willWallBlockPawns());
  }
}
