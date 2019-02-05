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

  @Override
  public Board enactTurn(Turn turn, Board board) {
    if (turn instanceof MovePawnTurn) {
      return enactMovePawnTurn((MovePawnTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  // TODO this API is disgusting
  private Board enactMovePawnTurn(MovePawnTurn turn, Board board) {
    var sourceCoord = turn.getSource();
    var destinationCoord = turn.getDestination();
    var sourceCell = board.getCell(sourceCoord);

    Map<Coordinate, BoardCell> newCells = new HashMap<>();
    var newSourceCell = board.getCell(sourceCoord).setPiece(NullPiece.INSTANCE);
    var newDestCell = board.getCell(destinationCoord).setPiece(sourceCell.getPiece());
    newCells.put(sourceCoord, newSourceCell);
    newCells.put(destinationCoord, newDestCell);

    return board.updateBoardCells(newCells);
  }
}
