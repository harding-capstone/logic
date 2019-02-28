package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;

public interface PlaceWallTurnValidationRule extends TurnValidationRule<PlaceWallTurn> {

  static PlaceWallTurnValidationRule areCoordinatesUnique() {
    return (turn, match) -> {
      var c1 = turn.getFirstCoordinate();
      var vertex = turn.getVertex();
      var c2 = turn.getSecondCoordinate();

      if (c1.equals(c2) || c1.equals(vertex) || c2.equals(vertex)) {
        return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_UNIQUE);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static PlaceWallTurnValidationRule isVertexFree() {
    return (turn, match) -> {
      var vertex = turn.getVertex();
      var board = match.getBoard();
      if (board.hasWall(vertex)) {
        return new TurnValidationResult(ErrorMessage.VERTEX_TAKEN);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static PlaceWallTurnValidationRule areCoordinatesAdjacentToVertex() {
    return (turn, match) -> {
      var c1 = turn.getFirstCoordinate();
      var vertex = turn.getVertex();
      var c2 = turn.getSecondCoordinate();

      var distC1 = Coordinate.calculateManhattanDistance(c1, vertex);
      var distC2 = Coordinate.calculateManhattanDistance(c2, vertex);

      if (distC1 == 1 && distC2 == 1) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_ADJACENT_TO_VERTEX);
      }
    };
  }

  static PlaceWallTurnValidationRule areCoordinatesStraight() {
    return (turn, match) -> {
      var c1 = turn.getFirstCoordinate();
      var vertex = turn.getVertex();
      var c2 = turn.getSecondCoordinate();

      var isC1Card = Coordinate.areCoordinatesCardinal(c1, vertex);
      var isC2Card = Coordinate.areCoordinatesCardinal(c2, vertex);

      if (isC1Card && isC2Card) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.COORDIANTES_NOT_STRAIGHT);
      }
    };
  }

  static PlaceWallTurnValidationRule areCoordinatesValid() {
    return (turn, match) -> {
      var board = match.getBoard();
      if (board.isCoordinateInvalid(turn.getFirstCoordinate())
          || board.isCoordinateInvalid(turn.getSecondCoordinate())
          || board.isCoordinateInvalid(turn.getVertex())) {
        return new TurnValidationResult(ErrorMessage.COORDINATE_INVALID);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static PlaceWallTurnValidationRule vertexCoordinateIsVertexCell() {
    return (turn, match) -> {
      var vertex = turn.getVertex();
      var board = match.getBoard();
      if (board.isVertexBoardCell(vertex)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.VERTEX_NOT_ON_VERTEX_CELL);
      }
    };
  }

  static PlaceWallTurnValidationRule coordinatesAreWallCells() {
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
        .and(coordinatesAreWallCells())
        .and(areCoordinatesEmpty())
        .and(doesPlayerHaveWallsToPlace())
        .and(willWallBlockPawns())
        .and(vertexCoordinateIsVertexCell())
        .and(areCoordinatesStraight())
        .and(areCoordinatesAdjacentToVertex())
        .and(isVertexFree())
        .and(areCoordinatesUnique());
  }
}
