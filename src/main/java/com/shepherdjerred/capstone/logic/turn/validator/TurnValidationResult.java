package com.shepherdjerred.capstone.logic.turn.validator;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class TurnValidationResult {

  private final boolean isError;
  private final List<ErrorMessage> errors;

  public TurnValidationResult(boolean isError) {
    this.isError = isError;
    this.errors = new ArrayList<>();
  }

  public TurnValidationResult(boolean isError, ErrorMessage error) {
    this.isError = isError;
    this.errors = new ArrayList<>();
    errors.add(error);
  }

  public TurnValidationResult(boolean isError, List<ErrorMessage> errors) {
    this.isError = isError;
    this.errors = errors;
  }

  public static TurnValidationResult combine(TurnValidationResult l, TurnValidationResult r) {
    List<ErrorMessage> errors = new ArrayList<>();
    errors.addAll(l.getErrors());
    errors.addAll(r.getErrors());
    return new TurnValidationResult(l.isError || r.isError, errors);
  }

  public enum ErrorMessage {
    NULL,
    MOVE_IS_DIAGONAL,
    NOT_OWNER_OF_PIECE,
    PIECE_NOT_PAWN,
    DESTINATION_NOT_EMPTY,
    SOURCE_CELL_TYPE_NOT_PAWN,
    DESTINATION_CELL_TYPE_NOT_PAWN,
    WALL_IS_BLOCKING,
    MOVE_TOO_FAR,
    NO_WALLS_TO_PLACE,
    NOT_WALL_CELL
  }
}
