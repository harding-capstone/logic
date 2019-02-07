package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import java.util.function.BiFunction;

public interface MovePawnTurnValidationRules extends
    BiFunction<MovePawnTurn, Match, TurnValidationResult> {

  static MovePawnTurnValidationRules isMoveOneSpaceAway() {
    return (turn, match) -> {
      if (Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination()) == 2) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.MOVE_TOO_FAR);
      }
    };
  }

  static MovePawnTurnValidationRules isWallBlocking() {
    return (turn, match) -> {
      var coordinateBetween = Coordinate.getMidpoint(turn.getSource(), turn.getDestination());
      System.out.println(coordinateBetween);
      if (match.getBoard().hasPiece(coordinateBetween)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.WALL_IS_BLOCKING);
      }
    };
  }

  static MovePawnTurnValidationRules isSourceCellTypePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().isPawnCell(source)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.SOURCE_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRules isDestinationCellTypePawn() {
    return (turn, match) -> {
      var destination = turn.getDestination();
      if (match.getBoard().isPawnCell(destination)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.DESTINATION_CELL_TYPE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRules isDestinationPieceEmpty() {
    return (turn, match) -> {
      var destination = turn.getDestination();
      if (match.getBoard().isEmpty(destination)) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.DESTINATION_NOT_EMPTY);
      }
    };
  }

  static MovePawnTurnValidationRules isSourcePiecePawn() {
    return (turn, match) -> {
      var source = turn.getSource();
      if (match.getBoard().getPiece(source) instanceof PawnPiece) {
        return new TurnValidationResult(false);
      } else {
        return new TurnValidationResult(true, ErrorMessage.PIECE_NOT_PAWN);
      }
    };
  }

  static MovePawnTurnValidationRules isPieceOwnedByPlayer() {
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

  static MovePawnTurnValidationRules isMoveCardinal() {
    return (turn, match) -> {
      var source = turn.getSource();
      var destination = turn.getDestination();
      if (Coordinate.areCoordinatesDiagonal(source, destination)) {
        return new TurnValidationResult(true, ErrorMessage.MOVE_IS_DIAGONAL);
      } else {
        return new TurnValidationResult(false);
      }
    };
  }

  default MovePawnTurnValidationRules and(MovePawnTurnValidationRules other) {
    return (turn, match) -> TurnValidationResult.combine(this.apply(turn, match),
        other.apply(turn, match));
  }

  static MovePawnTurnValidationRules all() {
    return isDestinationCellTypePawn()
        .and(isDestinationPieceEmpty())
        .and(isMoveCardinal())
        .and(isMoveOneSpaceAway())
        .and(isPieceOwnedByPlayer())
        .and(isSourceCellTypePawn())
        .and(isWallBlocking())
        .and(isSourcePiecePawn());
  }
}
