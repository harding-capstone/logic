package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.match.MatchState;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum MovePawnTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public boolean isTurnValid(Turn turn, MatchState matchState) {
    if (turn instanceof MovePawnTurn) {
      return isMovePawnTurnValid((MovePawnTurn) turn, matchState);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  private boolean isMovePawnTurnValid(MovePawnTurn turn, MatchState matchState) {
    return isSourceCellTypePawn(turn, matchState)
        && isDestinationCellTypePawn(turn, matchState)
        && isSourcePiecePawn(turn, matchState)
        && isDestinationPieceEmpty(turn, matchState)
        && isPieceOwnedByPlayer(turn, matchState)
        && isMoveOnePawnSpaceAway(turn)
        && isMoveNotDiagonal(turn)
        && isWallBlocking(turn, matchState);
  }

  private boolean isMoveOnePawnSpaceAway(MovePawnTurn turn) {
    return Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination()) == 2;
  }

  private boolean isWallBlocking(MovePawnTurn turn, MatchState matchState) {
    var coordinateBetween = Coordinate.getMidpoint(turn.getSource(), turn.getDestination());
    return matchState.getBoardState().hasPiece(coordinateBetween);
  }

  private boolean isSourceCellTypePawn(MovePawnTurn turn, MatchState matchState) {
    var source = turn.getSource();
    return matchState.getBoardState().getBoardCell(source) == BoardCell.PAWN;
  }

  private boolean isDestinationCellTypePawn(MovePawnTurn turn, MatchState matchState) {
    var destination = turn.getDestination();
    return matchState.getBoardState().getBoardCell(destination) == BoardCell.PAWN;
  }

  private boolean isDestinationPieceEmpty(MovePawnTurn turn, MatchState matchState) {
    var destination = turn.getDestination();
    return matchState.getBoardState().isEmpty(destination);
  }

  private boolean isSourcePiecePawn(MovePawnTurn turn, MatchState matchState) {
    var source = turn.getSource();
    return matchState.getBoardState().getPiece(source) instanceof PawnPiece;
  }

  private boolean isPieceOwnedByPlayer(MovePawnTurn turn, MatchState matchState) {
    var source = turn.getSource();
    var mover = turn.getCauser();
    var sourcePieceOwner = matchState.getBoardState().getPiece(source).getOwner();

    return mover == sourcePieceOwner;
  }

  private boolean isMoveNotDiagonal(MovePawnTurn turn) {
    var source = turn.getSource();
    var destination = turn.getDestination();
    return !Coordinate.areCoordinatesDiagonal(source, destination);
  }
}
