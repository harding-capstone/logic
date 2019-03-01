package com.shepherdjerred.capstone.logic.turn.validator.placewall;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.Coordinate.Direction;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class WallDoesntBlockPawnsValidatorRule implements ValidatorRule<PlaceWallTurn> {

  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
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
}
