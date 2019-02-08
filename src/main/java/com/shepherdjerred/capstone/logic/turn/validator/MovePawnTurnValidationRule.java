package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;

// TODO having rules for both moves and jumps in here is weird, might want to extract
public interface MovePawnTurnValidationRule extends TurnValidationRules<MovePawnTurn> {

  static MovePawnTurnValidationRule isSpaceOneCellAway() {
    return (turn, match) -> {
      // We check if the distance equals two because wall cells count in the calculation
      if (Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination()) == 2) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.MOVE_TOO_FAR);
      }
    };
  }

  static MovePawnTurnValidationRule isWallBlocking() {
    return (turn, match) -> {
      var coordinateBetween = Coordinate.getMidpoint(turn.getSource(), turn.getDestination());
      System.out.println(coordinateBetween);
      if (match.getBoard().hasPiece(coordinateBetween)) {
        return new TurnValidationResult(true, ErrorMessage.WALL_IS_BLOCKING);
      } else {
        return new TurnValidationResult(false);
      }
    };
  }

  static MovePawnTurnValidationRule isSourceCellTypePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().isPawnBoardCell(source)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.SOURCE_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationCellTypePawn() {
    return (turn, match) -> {
      var destination = turn.getDestination();
      if (match.getBoard().isPawnBoardCell(destination)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.DESTINATION_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isDestinationPieceEmpty() {
    return (turn, match) -> {
      var destination = turn.getDestination();
      if (match.getBoard().isEmpty(destination)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.DESTINATION_NOT_EMPTY);
      }
    };
  }

  static MovePawnTurnValidationRule isSourcePiecePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().getPiece(source) instanceof PawnPiece) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.PIECE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRule isPieceOwnedByPlayer() {
    return (turn, match) -> {
      var source = turn.getSource();
      var mover = turn.getCauser();
      var sourcePieceOwner = match.getBoard().getPiece(source).getOwner();

      if (mover == sourcePieceOwner) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.NOT_OWNER_OF_PIECE);
      }
    };
  }

  static MovePawnTurnValidationRule isMoveCardinal() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();
      if (Coordinate.areCoordinatesCardinal(source, destination)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.MOVE_IS_DIAGONAL);
      }
    };
  }

  static TurnValidationRules<MovePawnTurn> jumpDiagonal() {
    // TODO
    return isDestinationCellTypePawn()
        .and(isDestinationPieceEmpty())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isSourcePiecePawn());
  }

  static TurnValidationRules<MovePawnTurn> jumpStraight() {
    // TODO
    return isDestinationCellTypePawn()
        .and(isDestinationPieceEmpty())
        .and(isMoveCardinal())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isSourcePiecePawn());
  }

  static TurnValidationRules<MovePawnTurn> normal() {
    return isDestinationCellTypePawn()
        .and(isDestinationPieceEmpty())
        .and(isMoveCardinal())
        .and(isSpaceOneCellAway())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isWallBlocking())
        .and(isSourcePiecePawn());
  }
}