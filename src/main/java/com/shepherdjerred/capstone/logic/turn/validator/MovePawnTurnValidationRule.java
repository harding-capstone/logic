package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.Coordinate.Direction;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;

// TODO having rules for both moves and jumps in here is kinda weird, might want to extract?
public interface MovePawnTurnValidationRule extends TurnValidationRule<MovePawnTurn> {

  static MovePawnTurnValidationRule isSourceCoordinateValid() {
    return (turn, match) -> {
      var board = match.getBoard();
      if (board.isCoordinateInvalid(turn.getSource())) {
        return new TurnValidationResult(ErrorMessage.SOURCE_COORDINATE_INVALID);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationCoordinateValid() {
    return (turn, match) -> {
      var board = match.getBoard();
      if (board.isCoordinateInvalid(turn.getDestination())) {
        return new TurnValidationResult(ErrorMessage.DESTINATION_COORDINATE_INVALID);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static MovePawnTurnValidationRule isSpaceOneAway() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // We check if the distance equals two because wall cells count in the calculation
      if (dist == 2) {
        return new TurnValidationResult();
      } else if (dist > 2) {
        return new TurnValidationResult(ErrorMessage.MOVE_TOO_FAR);
      } else {
        return new TurnValidationResult(true);
      }
    };
  }

  static MovePawnTurnValidationRule isTwoSpacesAway() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // We check if the distance equals two because wall cells count in the calculation
      if (dist == 4) {
        return new TurnValidationResult();
      } else if (dist > 4) {
        return new TurnValidationResult(ErrorMessage.MOVE_TOO_FAR);
      } else {
        return new TurnValidationResult(true);
      }
    };
  }

  static MovePawnTurnValidationRule isWallBlocking() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // If the distance != 2 then we can't do this check
      if (dist != 2) {
        return new TurnValidationResult();
      }

      var coordinateBetween = Coordinate.getMidpoint(turn.getSource(), turn.getDestination());
      if (match.getBoard().hasPiece(coordinateBetween)) {
        return new TurnValidationResult(ErrorMessage.WALL_IS_BLOCKING);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static MovePawnTurnValidationRule isSourceCellTypePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().isPawnBoardCell(source)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.SOURCE_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationCellTypePawn() {
    return (turn, match) -> {
      var destination = turn.getDestination();
      if (match.getBoard().isCoordinateInvalid(destination)) {
        return new TurnValidationResult(true);
      }

      if (match.getBoard().isPawnBoardCell(destination)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.DESTINATION_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationPieceEmpty() {
    return (turn, match) -> {
      var destination = turn.getDestination();
      if (match.getBoard().isEmpty(destination)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.DESTINATION_NOT_EMPTY);
      }
    };
  }

  static MovePawnTurnValidationRule isSourcePiecePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().getPiece(source) instanceof PawnPiece) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.PIECE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isPieceOwnedByPlayer() {
    return (turn, match) -> {
      var source = turn.getSource();
      var mover = turn.getCauser();
      var sourcePieceOwner = match.getBoard().getPiece(source).getOwner();

      if (mover == sourcePieceOwner) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.NOT_OWNER_OF_PIECE);
      }
    };
  }

  static MovePawnTurnValidationRule isMoveCardinal() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();
      if (Coordinate.areCoordinatesCardinal(source, destination)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.MOVE_IS_DIAGONAL);
      }
    };
  }

  static MovePawnTurnValidationRule isMoveDiagonal() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();
      if (Coordinate.areCoordinatesDiagonal(source, destination)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.MOVE_IS_CARDINAL);
      }
    };
  }

  static Direction getDirectionOfMove(Coordinate source, Coordinate destination) {
    if (source.getX() < destination.getX()) {
      return Direction.right;
    } else if (source.getX() > destination.getX()) {
      return Direction.left;
    } else if (source.getY() < destination.getY()) {
      return Direction.above;
    } else {
      return Direction.below;
    }
  }

  static boolean isFreeToJump(Coordinate source, Match match, Direction direction)
  {
    var boardSpaceDirectlyAdjacent = source.adjacent(direction, 1);
    var pawnSpaceAdjacent = source.adjacent(direction, 2);

    boolean isNotBlockedByWall = match.getBoard().isEmpty(boardSpaceDirectlyAdjacent);
    boolean isBlockedByPawn = match.getBoard().hasPiece(pawnSpaceAdjacent);

    return isNotBlockedByWall && isBlockedByPawn;
  }

  static boolean isStraightJumpAllowed(Coordinate source, Match match, Direction direction) {
    var boardSpaceBehindOpponentPawn = source.adjacent(direction, 3);

    boolean isEmptyBehindNextPawnSpace = match.getBoard().isEmpty(boardSpaceBehindOpponentPawn);

    return  isFreeToJump(source, match, direction) && isEmptyBehindNextPawnSpace;
  }

  static MovePawnTurnValidationRule isJumpValid() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();

      Direction directionOfMove = getDirectionOfMove(source, destination);

      if (Coordinate.calculateManhattanDistance(source, destination) == 4) {
        if (isStraightJumpAllowed(source, match, directionOfMove)) {
          return new TurnValidationResult();
        } else {
          return new TurnValidationResult(ErrorMessage.JUMP_NOT_ALLOWED);
        }
      } else {
        return new TurnValidationResult(ErrorMessage.MOVE_IS_NOT_A_JUMP);
      }
    };
  }

  static boolean isDiagonalJumpAllowed(Coordinate source, Match match, Direction direction) {
    var boardSpaceBehindOpponentPawn = source.adjacent(direction, 3);

    boolean isWallBehindNextPawnSpace = match.getBoard().hasPiece(boardSpaceBehindOpponentPawn);

    return isFreeToJump(source, match, direction) && isWallBehindNextPawnSpace;
  }

  static boolean isDiagonalMoveLegal(Coordinate source, Match match, Boolean isMoveAbove, Boolean isMoveRight) {
    if (isMoveAbove && isMoveRight) {
      return isDiagonalJumpAllowed(source, match, Direction.above) || isDiagonalJumpAllowed(source, match, Direction.right);
    } else if (isMoveAbove) {
      return isDiagonalJumpAllowed(source, match, Direction.above) || isDiagonalJumpAllowed(source, match, Direction.left);
    } else if (isMoveRight) {
      return isDiagonalJumpAllowed(source, match, Direction.below) || isDiagonalJumpAllowed(source, match, Direction.right);
    } else {
      return isDiagonalJumpAllowed(source, match, Direction.below) || isDiagonalJumpAllowed(source, match, Direction.left);
    }
  }

  static MovePawnTurnValidationRule isDiagonalJumpValid() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();

      boolean isMoveAbove = source.getY() < destination.getY();
      boolean isMoveRight = source.getX() < destination.getX();

      boolean isDiagonalMoveLegal = isDiagonalMoveLegal(source, match, isMoveAbove, isMoveRight);

      if (isDiagonalMoveLegal) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.DIAGONAL_MOVE_NOT_ALLOWED);
      }
    };
  }

  static TurnValidationRule<MovePawnTurn> jumpDiagonal() {
    return isSourceCoordinateValid()
        .and(isDestinationCoordinateValid())
        .and(isDestinationCellTypePawn())
        .and(isDestinationPieceEmpty())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isSourcePiecePawn())
        .and(isTwoSpacesAway())
        .and(isMoveDiagonal())
        .and(isDiagonalJumpValid())
        .and(isWallBlocking());
  }

  static TurnValidationRule<MovePawnTurn> jumpStraight() {
    return isSourceCoordinateValid()
        .and(isDestinationCoordinateValid())
        .and(isDestinationCellTypePawn())
        .and(isDestinationPieceEmpty())
        .and(isMoveCardinal())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isSourcePiecePawn())
        .and(isJumpValid());
  }

  static TurnValidationRule<MovePawnTurn> normal() {
    return isSourceCoordinateValid()
        .and(isDestinationCoordinateValid())
        .and(isDestinationCellTypePawn())
        .and(isDestinationPieceEmpty())
        .and(isMoveCardinal())
        .and(isSpaceOneAway())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isWallBlocking())
        .and(isSourcePiecePawn());
  }
}
