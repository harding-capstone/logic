package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum MovePawnTurnValidator implements TurnValidator {
  INSTANCE;

  @Override
  public boolean isTurnValid(Turn turn, Match match) {
    if (turn instanceof MovePawnTurn) {
      return isMovePawnTurnValid((MovePawnTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  private boolean isMovePawnTurnValid(MovePawnTurn turn, Match match) {
    return isSourceCellTypePawn(turn, match)
        && isDestinationCellTypePawn(turn, match)
        && isSourcePiecePawn(turn, match)
        && isDestinationPieceEmpty(turn, match)
        && isPieceOwnedByPlayer(turn, match)
        && isMoveOnePawnSpaceAway(turn)
        && isMoveNotDiagonal(turn)
        && isWallBlocking(turn, match);
  }

  private boolean isMoveOnePawnSpaceAway(MovePawnTurn turn) {
    return Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination()) == 2;
  }

  private boolean isWallBlocking(MovePawnTurn turn, Match match) {
    var coordinateBetween = Coordinate.getMidpoint(turn.getSource(), turn.getDestination());
    return match.getBoard().hasPiece(coordinateBetween);
  }

  private boolean isSourceCellTypePawn(MovePawnTurn turn, Match match) {
    var source = turn.getSource();
    return match.getBoard().getBoardCell(source) == BoardCell.PAWN;
  }

  private boolean isDestinationCellTypePawn(MovePawnTurn turn, Match match) {
    var destination = turn.getDestination();
    return match.getBoard().getBoardCell(destination) == BoardCell.PAWN;
  }

  private boolean isDestinationPieceEmpty(MovePawnTurn turn, Match match) {
    var destination = turn.getDestination();
    return match.getBoard().isEmpty(destination);
  }

  private boolean isSourcePiecePawn(MovePawnTurn turn, Match match) {
    var source = turn.getSource();
    return match.getBoard().getPiece(source) instanceof PawnPiece;
  }

  private boolean isPieceOwnedByPlayer(MovePawnTurn turn, Match match) {
    var source = turn.getSource();
    var mover = turn.getCauser();
    var sourcePieceOwner = match.getBoard().getPiece(source).getOwner();

    return mover == sourcePieceOwner;
  }

  private boolean isMoveNotDiagonal(MovePawnTurn turn) {
    var source = turn.getSource();
    var destination = turn.getDestination();
    return !Coordinate.areCoordinatesDiagonal(source, destination);
  }
}
