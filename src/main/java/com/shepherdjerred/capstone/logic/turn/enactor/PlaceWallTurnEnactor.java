package com.shepherdjerred.capstone.logic.turn.enactor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.cell.BoardCell;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.HashMap;
import java.util.Map;

public enum PlaceWallTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param match The initial match state
   * @return The initial match state transformed by the turn
   */
  @Override
  public Match enactTurn(Turn turn, Match match) {
    if (turn instanceof PlaceWallTurn) {
      return enactPlaceWallTurn((PlaceWallTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  private Match enactPlaceWallTurn(PlaceWallTurn turn, Match match) {
    var board = match.getBoard();
    var updatedCells = getUpdatedCells(turn, board);
    var newBoard = board.updateBoardCells(updatedCells);
    var updatedWalls = updatePlayerWalls(turn, match);
    return Match.builder()
        .board(newBoard)
        .matchSettings(match.getMatchSettings())
        .turnEnactorFactory(match.getTurnEnactorFactory())
        .turnValidatorFactory(match.getTurnValidatorFactory())
        .currentPlayerTurn(match.getNextPlayer())
        .playerWalls(updatedWalls)
        .build();
  }

  private Map<Coordinate, BoardCell> getUpdatedCells(PlaceWallTurn turn, Board board) {
    var firstCoordinate = turn.getFirstCoordinate();
    var secondCoordinate = turn.getSecondCoordinate();
    var turnCauser = turn.getCauser();

    var firstCoordinateCell = board.getCell(firstCoordinate);
    var secondCoordinateCell = board.getCell(secondCoordinate);

    var updatedCells = new HashMap<Coordinate, BoardCell>();
    var updatedFirstCoordinateCell = firstCoordinateCell.setPiece(new WallPiece(turnCauser));
    var updatedSecondCoordinateCell = secondCoordinateCell.setPiece(new WallPiece(turnCauser));

    updatedCells.put(firstCoordinate, updatedFirstCoordinateCell);
    updatedCells.put(secondCoordinate, updatedSecondCoordinateCell);
    return updatedCells;
  }

  private ImmutableMap<Player, Integer> updatePlayerWalls(PlaceWallTurn turn, Match match) {
    var target = turn.getCauser();
    var oldWallValue = match.getRemainingWallCount(target);
    var updatedPlayerWalls = ImmutableMap.<Player, Integer>builder()
        .put(turn.getCauser(), oldWallValue - 1).build();
    return ImmutableMap.<Player, Integer>builder()
        .putAll(updatedPlayerWalls)
        .putAll(Maps.difference(match.getPlayerWalls(), updatedPlayerWalls).entriesOnlyOnLeft())
        .build();
  }
}
