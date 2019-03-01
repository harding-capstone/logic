package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.Coordinate.Direction;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;

// TODO having rules for both moves and jumps in here is kinda weird, might want to extract?
public interface MovePawnTurnValidationRule extends TurnValidationRule<MovePawnTurn> {

  static MovePawnTurnValidationRule isSourceSameAsMatchPawnLocation() {
    return (turn, match) -> {
      if (turn.getSource().equals(match.getBoard().getPawnLocation(turn.getCauser()))) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.INCORRECT_SOURCE);
      }
    };
  }

  static MovePawnTurnValidationRule areSourceAndDestinationDifferent() {
    return (turn, match) -> {
      if (turn.getSource().equals(turn.getDestination())) {
        return new TurnValidationResult(ErrorMessage.COORDINATES_NOT_UNIQUE);
      } else {
        return new TurnValidationResult();
      }
    };
  }

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

  static MovePawnTurnValidationRule isDestinationOnePawnSpaceAway() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // We check if the distance equals two because wall cells count in the calculation
      if (dist == 2) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.MOVE_NOT_ONE_SPACE_AWAY);
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationTwoPawnSpacesAway() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // We check if the distance equals four because wall cells count in the calculation
      if (dist == 4) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.MOVE_NOT_TWO_SPACES_AWAY);
      }
    };
  }

  static MovePawnTurnValidationRule isWallBetweenSourceAndDestination() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // If the distance != 2 then we can't do this check
      if (dist != 2) {
        System.out.println("Distance != 2");
        return new TurnValidationResult(ErrorMessage.NULL);
      }

      var coordinateBetween = Coordinate.calculateMidpoint(turn.getSource(), turn.getDestination());
      if (match.getBoard().hasPiece(coordinateBetween)) {
        return new TurnValidationResult(ErrorMessage.WALL_BETWEEN_SOURCE_AND_DESTINATION);
      } else {
        return new TurnValidationResult();
      }
    };
  }

  static MovePawnTurnValidationRule isSourceBoardCellTypePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().isPawnBoardCell(source)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.SOURCE_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationBoardCellTypePawn() {
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
        return new TurnValidationResult(ErrorMessage.SOURCE_PIECE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isSourcePieceOwnedByPlayer() {
    return (turn, match) -> {
      var source = turn.getSource();
      var mover = turn.getCauser();
      var sourcePieceOwner = match.getBoard().getPiece(source).getOwner();

      if (mover == sourcePieceOwner) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.SOURCE_PIECE_NOT_OWNED_BY_PLAYER);
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
        return new TurnValidationResult(ErrorMessage.MOVE_NOT_CARDINAL);
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
        return new TurnValidationResult(ErrorMessage.MOVE_NOT_DIAGONAL);
      }
    };
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

  static Direction getDirectionOfMove(Coordinate source, Coordinate destination) {
    if (source.getX() < destination.getX()) {
      return Direction.RIGHT;
    } else if (source.getX() > destination.getX()) {
      return Direction.LEFT;
    } else if (source.getY() < destination.getY()) {
      return Direction.ABOVE;
    } else {
      return Direction.BELOW;
    }
  }

  static boolean isFreeToJump(Coordinate source, Match match, Direction direction) {
    var boardSpaceDirectlyAdjacent = source.adjacent(direction, 1);
    var pawnSpaceAdjacent = source.adjacent(direction, 2);

    boolean isNotBlockedByWall = match.getBoard().isEmpty(boardSpaceDirectlyAdjacent);
    boolean isBlockedByPawn = match.getBoard().hasPiece(pawnSpaceAdjacent);

    return isNotBlockedByWall && isBlockedByPawn;
  }

  static boolean isStraightJumpAllowed(Coordinate source, Match match, Direction direction) {
    var boardSpaceBehindOpponentPawn = source.adjacent(direction, 3);

    boolean isEmptyBehindNextPawnSpace = match.getBoard().isEmpty(boardSpaceBehindOpponentPawn);

    return isFreeToJump(source, match, direction) && isEmptyBehindNextPawnSpace;
  }

  static boolean isDiagonalJumpAllowed(Coordinate source, Match match, Direction direction) {
    var boardSpaceBehindOpponentPawn = source.adjacent(direction, 3);

    boolean isWallBehindNextPawnSpace = match.getBoard().hasPiece(boardSpaceBehindOpponentPawn);

    return isFreeToJump(source, match, direction) && isWallBehindNextPawnSpace;
  }

  static boolean isDiagonalMoveLegal(Coordinate source,
      Match match,
      Boolean isMoveAbove,
      Boolean isMoveRight) {
    if (isMoveAbove && isMoveRight) {
      return isDiagonalJumpAllowed(source, match, Direction.ABOVE) || isDiagonalJumpAllowed(source,
          match,
          Direction.RIGHT);
    } else if (isMoveAbove) {
      return isDiagonalJumpAllowed(source, match, Direction.ABOVE) || isDiagonalJumpAllowed(source,
          match,
          Direction.LEFT);
    } else if (isMoveRight) {
      return isDiagonalJumpAllowed(source, match, Direction.BELOW) || isDiagonalJumpAllowed(source,
          match,
          Direction.RIGHT);
    } else {
      return isDiagonalJumpAllowed(source, match, Direction.BELOW) || isDiagonalJumpAllowed(source,
          match,
          Direction.LEFT);
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

  static MovePawnTurnValidationRule isPieceBetweenSourceAndDestination() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();
      var midpoint = Coordinate.calculateMidpoint(source, destination);

      if (match.getBoard().isEmpty(midpoint)) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.PIECE_NOT_BETWEEN_SOURCE_AND_DESTINATION);
      }
    };
  }

  static TurnValidationRule<MovePawnTurn> jumpDiagonal() {
    return shared()
        .and(isDestinationTwoPawnSpacesAway())
        .and(isMoveDiagonal())
        .and(isDiagonalJumpValid());
  }

  static TurnValidationRule<MovePawnTurn> jumpStraight() {
    return shared()
        .and(isMoveCardinal())
        .and(isJumpValid())
        .and(isDestinationTwoPawnSpacesAway())
        .and(isPieceBetweenSourceAndDestination());
  }

  static TurnValidationRule<MovePawnTurn> normal() {
    return shared()
        .and(isMoveCardinal())
        .and(isDestinationOnePawnSpaceAway())
        .and(isWallBetweenSourceAndDestination());
  }

  private static TurnValidationRule<MovePawnTurn> shared() {
    return isSourceCoordinateValid()
        .and(isDestinationCoordinateValid())
        .and(isDestinationBoardCellTypePawn())
        .and(isDestinationPieceEmpty())
        .and(isSourcePieceOwnedByPlayer())
        .and(isSourcePiecePawn())
        .and(areSourceAndDestinationDifferent())
        .and(isSourceSameAsMatchPawnLocation())
        .and(isSourceBoardCellTypePawn());
  }
}
