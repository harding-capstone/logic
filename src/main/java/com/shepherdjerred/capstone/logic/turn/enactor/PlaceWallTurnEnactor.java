package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public final class PlaceWallTurnEnactor implements TurnEnactor {

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param board The board state
   * @return The board state transformed by the turn
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
    var wallLocation = new WallPieceLocation(turn.getFirstCoordinate(),
        turn.getVertex(),
        turn.getSecondCoordinate());
    return board.placeWall(turn.getCauser(), wallLocation);
  }
}
