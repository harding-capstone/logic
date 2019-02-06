package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
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
        && isSourcePieceTypePawn(turn, match)
        && isDestinationPieceEmpty(turn, match)
        && isPieceOwnedByPlayer(turn, match)
        && isMoveOnePawnSpaceAway(turn, match)
        && isMoveNotDiagonal(turn)
        && isWallBlocking(turn, match);
  }

  private boolean isMoveOnePawnSpaceAway(MovePawnTurn turn, Match match) {
    return Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination()) == 2;
  }

  private boolean isWallBlocking(MovePawnTurn turn, Match match) {
    var coordinateBetween = Coordinate.getMidpoint(turn.getSource(), turn.getDestination());
    var cellBetween = match.getBoard().getCell(coordinateBetween);

    return cellBetween.hasWall();
  }

  private boolean isSourceCellTypePawn(MovePawnTurn turn, Match match) {
    var source = turn.getSource();
    return match.getBoard().isPawnCell(source);
  }

  private boolean isDestinationCellTypePawn(MovePawnTurn turn, Match match) {
    var destination = turn.getDestination();
    return match.getBoard().isPawnCell(destination);
  }

  private boolean isDestinationPieceEmpty(MovePawnTurn turn, Match match) {
    var destination = turn.getDestination();
    return match.getBoard().isPieceEmpty(destination);
  }

  private boolean isSourcePieceTypePawn(MovePawnTurn turn, Match match) {
    var source = turn.getSource();
    return match.getBoard().getCell(source).getPiece() instanceof PawnPiece;
  }

  private boolean isPieceOwnedByPlayer(MovePawnTurn turn, Match match) {
    var source = turn.getSource();
    var mover = turn.getCauser();
    var sourcePieceOwner = match.getBoard().getCell(source).getPiece().getOwner();

    return mover == sourcePieceOwner;
  }

  private boolean isMoveNotDiagonal(MovePawnTurn turn) {
    var source = turn.getSource();
    var destination = turn.getDestination();
    return !Coordinate.areCoordinatesDiagonal(source, destination);
  }
}
