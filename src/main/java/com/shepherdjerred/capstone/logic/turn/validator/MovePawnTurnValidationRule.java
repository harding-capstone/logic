package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
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

  static MovePawnTurnValidationRule isWallBlocking() {
    return (turn, match) -> {
      var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

      // If the distance != 2 then we can't do this check
      if (dist != 2) {
        return new TurnValidationResult(true);
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

  static TurnValidationRule<MovePawnTurn> jumpDiagonal() {
    // TODO Check if a diagonal jump is allowed (is there a wall behind the pawn we're jumping?
    // TODO Check if a wall is blocking the diagonal jump
    // TODO Check that the distance of the jump is valid
    // TODO Check that the move isn't cardinal
    return isSourceCoordinateValid()
        .and(isDestinationCoordinateValid())
        .and(isDestinationCellTypePawn())
        .and(isDestinationPieceEmpty())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isSourcePiecePawn());
  }

  static TurnValidationRule<MovePawnTurn> jumpStraight() {
    // TODO Check that there is a pawn inbetween src and dest
    // TODO Check that there isn't a wall behind the pawn
    // TODO Check that the distance == 2
    return isSourceCoordinateValid()
        .and(isDestinationCoordinateValid())
        .and(isDestinationCellTypePawn())
        .and(isDestinationPieceEmpty())
        .and(isMoveCardinal())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isSourcePiecePawn());
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
