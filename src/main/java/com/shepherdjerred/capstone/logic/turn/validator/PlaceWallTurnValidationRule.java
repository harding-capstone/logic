package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.Coordinate.Direction;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import java.util.LinkedList;
import java.util.List;

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

  static boolean canMakeStep(Coordinate coordinate, Match match, Direction direction) {
    return match.getBoard().isEmpty(coordinate.adjacent(direction, 1)) && match.getBoard().isPawnBoardCell(coordinate.adjacent(direction, 2)) && match.getBoard().isEmpty(coordinate.adjacent(direction, 2));
  }

  static Direction oppositeDirection(Direction direction) {
    if (direction == Direction.above) {
      return Direction.below;
    } else {
      return Direction.above;
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
      if (canMakeStep(coordinate, match, Direction.right)) {
        coordinate = coordinate.toRight(2);
        return foundPath(coordinate, match, direction, visited);
      }
      if (canMakeStep(coordinate, match, Direction.left)) {
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

      boolean foundPath = foundPath(coordinate, match, Direction.above, visited);

      visited = new int[17][17];

      coordinate = match.getBoard().getPawnLocation(PlayerId.TWO);

      foundPath = foundPath && foundPath(coordinate, match, Direction.below, visited);

      if (foundPath) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.WALL_BLOCKS_PATH);
      }

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
