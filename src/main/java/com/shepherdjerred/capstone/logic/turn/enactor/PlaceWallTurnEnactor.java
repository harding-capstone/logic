package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardCell;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.HashMap;
import java.util.Map;

public enum PlaceWallTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   * @param turn The turn to use when transforming the board
   * @param board The initial board state
   * @return The initial board state transformed by the turn
   */
  @Override
  public Board enactTurn(Turn turn, Board board) {
    if (turn instanceof PlaceWallTurn) {
      return enactPlaceWallTurn((PlaceWallTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  private Board enactPlaceWallTurn(PlaceWallTurn turn, Board board) {
    var updatedCells = getUpdatedCells(turn, board);
    return board.updateBoardCells(updatedCells);
  }

  private Map<Coordinate, BoardCell> getUpdatedCells(PlaceWallTurn turn, Board board) {
    var firstCoordinate = turn.getFirstCoordinate();
    var secondCoordinate = turn.getSecondCoordinate();
    var turnCauser = turn.getCauser();

    var firstCoordinateCell = board.getCell(firstCoordinate);
    var secondCoordinateCell = board.getCell(secondCoordinate);

    Map<Coordinate, BoardCell> updatedCells = new HashMap<>();
    var updatedFirstCoordinateCell = firstCoordinateCell.setPiece(new WallPiece(turnCauser));
    var updatedSecondCoordinateCell = secondCoordinateCell.setPiece(new WallPiece(turnCauser));

    updatedCells.put(firstCoordinate, updatedFirstCoordinateCell);
    updatedCells.put(secondCoordinate, updatedSecondCoordinateCell);
    return updatedCells;
  }
}
