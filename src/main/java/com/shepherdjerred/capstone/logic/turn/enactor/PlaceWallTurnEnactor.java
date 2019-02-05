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

  @Override
  public Board enactTurn(Turn turn, Board board) {
    if (turn instanceof PlaceWallTurn) {
      return enactPlaceWallTurn((PlaceWallTurn) turn, board);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  // TODO this API is disgusting
  private Board enactPlaceWallTurn(PlaceWallTurn turn, Board board) {
    var c1 = turn.getC1();
    var c2 = turn.getC2();
    var owner = turn.getCauser();

    Map<Coordinate, BoardCell> cellMap = new HashMap<>();
    var newC1Cell = board.getCell(c1).setPiece(new WallPiece(owner));
    var newC2Cell = board.getCell(c2).setPiece(new WallPiece(owner));

    cellMap.put(c1, newC1Cell);
    cellMap.put(c2, newC2Cell);

    return board.updateBoardCells(cellMap);
  }
}
