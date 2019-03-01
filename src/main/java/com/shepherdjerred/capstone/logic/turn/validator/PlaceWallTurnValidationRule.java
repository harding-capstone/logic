package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.Coordinate.Direction;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
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
        return new TurnValidationResult(ErrorMessage.VERTEX_NOT_FREE);
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
        return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_STRAIGHT);
      }
    };
  }

  static PlaceWallTurnValidationRule areCoordinatesValid() {
    return (turn, match) -> {
      var board = match.getBoard();
      if (board.isCoordinateInvalid(turn.getFirstCoordinate())
          || board.isCoordinateInvalid(turn.getSecondCoordinate())
          || board.isCoordinateInvalid(turn.getVertex())) {
        return new TurnValidationResult(ErrorMessage.COORDINATES_INVALID);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static PlaceWallTurnValidationRule vertexCoordinateIsVertexCell() {
    return (turn, match) -> {
      var vertex = turn.getVertex();
      var board = match.getBoard();
      if (board.isWallVertex(vertex)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.VERTEX_COORDINATE_IS_NOT_VERTEX_CELL);
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
        return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_WALL_CELLS);
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
        return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_EMPTY);
      }
    };
  }

  static PlaceWallTurnValidationRule doesPlayerHaveWallsToPlace() {
    return (turn, match) -> {
      var player = turn.getCauser();
      if (match.getWallsLeft(player) > 0) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.PLAYER_HAS_NO_WALLS);
      }
    };
  }

  static boolean canMakeStep(Coordinate coordinate, Match match, Direction direction) {
    return match.getBoard().isEmpty(coordinate.adjacent(direction, 1)) && match.getBoard().isPawnBoardCell(coordinate.adjacent(direction, 2)) && match.getBoard().isEmpty(coordinate.adjacent(direction, 2));
  }

  static Direction oppositeDirection(Direction direction) {
    if (direction == Direction.ABOVE) {
      return Direction.BELOW;
    } else {
      return Direction.ABOVE;
    }
  }

  static boolean foundPath(Coordinate coordinate, Match match, Direction direction, int[][] visited) {
    if (visited[coordinate.getX()][coordinate.getY()] == 1) {
      return false;
    }

    visited[coordinate.getX()][coordinate.getY()] = 1;

    if (coordinate.getY() < 16 && coordinate.getY() > 0) {
      if (canMakeStep(coordinate, match, direction)) {
        coordinate = coordinate.above(2);
        return foundPath(coordinate, match, direction, visited);
      }
      if (canMakeStep(coordinate, match, Direction.RIGHT)) {
        coordinate = coordinate.toRight(2);
        return foundPath(coordinate, match, direction, visited);
      }
      if (canMakeStep(coordinate, match, Direction.LEFT)) {
        coordinate = coordinate.toLeft(2);
        return foundPath(coordinate, match, direction, visited);
      }
      if (canMakeStep(coordinate, match, oppositeDirection(direction))) {
        coordinate = coordinate.below(2);
        return foundPath(coordinate, match, direction, visited);
      }

      return false;
    }

    return true;
  }

  // TODO
  static PlaceWallTurnValidationRule willWallBlockPawns() {
    return (turn, match) -> {
      int visited[][] = new int[17][17];

      var coordinate = match.getBoard().getPawnLocation(PlayerId.ONE);

      boolean foundPath = foundPath(coordinate, match, Direction.ABOVE, visited);

      visited = new int[17][17];

      coordinate = match.getBoard().getPawnLocation(PlayerId.TWO);

      foundPath = foundPath && foundPath(coordinate, match, Direction.BELOW, visited);

      if (foundPath) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.WALL_BLOCKS_PATH);
      }

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
