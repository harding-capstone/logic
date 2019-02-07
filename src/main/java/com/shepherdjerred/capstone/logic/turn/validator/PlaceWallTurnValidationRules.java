package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import java.util.function.BiFunction;

public interface PlaceWallTurnValidationRules extends
    BiFunction<PlaceWallTurn, Match, TurnValidationResult> {

  static PlaceWallTurnValidationRules isWallCell() {
    return (turn, match) -> {
      var board = match.getBoard();
      var firstCoordinate = turn.getFirstCoordinate();
      var secondCoordinate = turn.getSecondCoordinate();

      if (board.isWallCell(firstCoordinate)
          && board.isWallCell(secondCoordinate)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.NOT_WALL_CELL);
      }
    };
  }

  static PlaceWallTurnValidationRules areCoordinatesEmpty() {
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

  static PlaceWallTurnValidationRules doesPlayerHaveWallsToPlace() {
    return (turn, match) -> {
      var player = turn.getCauser();
      if (match.getRemainingWallCount(player) > 0) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.NO_WALLS_TO_PLACE);
      }
    };
  }

  static PlaceWallTurnValidationRules willWallBlockPawns() {
    return (turn, match) -> {
      // TODO
      return new TurnValidationResult(false);
    };
  }


  default PlaceWallTurnValidationRules and(PlaceWallTurnValidationRules other) {
    return (turn, match) -> TurnValidationResult.combine(this.apply(turn, match),
        other.apply(turn, match));
  }

  static PlaceWallTurnValidationRules all() {
    return isWallCell()
        .and(areCoordinatesEmpty())
        .and(doesPlayerHaveWallsToPlace())
        .and(willWallBlockPawns());
  }
}
