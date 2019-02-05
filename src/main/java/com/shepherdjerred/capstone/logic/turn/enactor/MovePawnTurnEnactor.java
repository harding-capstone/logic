package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardCell;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.HashMap;
import java.util.Map;

public enum MovePawnTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   * @param turn The turn to use when transforming the board
   * @param board The initial board state
   * @return The initial board state transformed by the turn
   */
  @Override
  public Board enactTurn(Turn turn, Board board) {
    if (turn instanceof MovePawnTurn) {
      return enactMovePawnTurn((MovePawnTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  private Board enactMovePawnTurn(MovePawnTurn turn, Board board) {
    var updatedCells = getUpdatedCells(turn, board);
    return board.updateBoardCells(updatedCells);
  }

  private Map<Coordinate, BoardCell> getUpdatedCells(MovePawnTurn turn, Board board) {
    var sourceCoordinate = turn.getSource();
    var destinationCoordinate = turn.getDestination();

    var sourceCell = board.getCell(sourceCoordinate);
    var destinationCell = board.getCell(destinationCoordinate);
    var piece = sourceCell.getPiece();

    Map<Coordinate, BoardCell> updatedCells = new HashMap<>();
    var updatedSourceCell = sourceCell.setPiece(NullPiece.INSTANCE);
    var updatedDestinationCell = destinationCell.setPiece(piece);

    updatedCells.put(sourceCoordinate, updatedSourceCell);
    updatedCells.put(destinationCoordinate, updatedDestinationCell);
    return updatedCells;
  }
}
