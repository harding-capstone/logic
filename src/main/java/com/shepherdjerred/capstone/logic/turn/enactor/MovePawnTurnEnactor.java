package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.cell.BoardCell;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.HashMap;
import java.util.Map;

public enum MovePawnTurnEnactor implements TurnEnactor {
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
    if (turn instanceof MovePawnTurn) {
      return enactMovePawnTurn((MovePawnTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  // TODO check for victory
  private Match enactMovePawnTurn(MovePawnTurn turn, Match match) {
    var board = match.getBoard();
    var updatedCells = getUpdatedCells(turn, board);
    var newBoard = board.updateBoardCells(updatedCells);
    return Match.builder()
        .board(newBoard)
        .matchSettings(match.getMatchSettings())
        .turnEnactorFactory(match.getTurnEnactorFactory())
        .turnValidatorFactory(match.getTurnValidatorFactory())
        .currentPlayerTurn(match.getNextPlayer())
        .playerWalls(match.getPlayerWalls())
        .status(match.getStatus())
        .build();
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
