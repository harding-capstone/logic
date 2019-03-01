package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import java.util.function.BiFunction;

public interface TurnValidationRule<T extends Turn> extends
    BiFunction<T, Match, TurnValidationResult> {

  static TurnValidationRule<Turn> isPlayersTurn() {
    return (turn, match) -> {
      if (turn.getCauser() == match.getActivePlayerId()) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.NOT_CAUSERS_TURN);
      }
    };
  }

  static TurnValidationRule<Turn> isMatchOver() {
    return (turn, match) -> {
      if (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {
        return new TurnValidationResult();
      } else {
        return new TurnValidationResult(ErrorMessage.GAME_ALREADY_OVER);
      }
    };
  }

  default TurnValidationRule<T> and(TurnValidationRule<T> other) {
    return (turn, match) -> TurnValidationResult.combine(this.apply(turn, match),
        other.apply(turn, match));
  }

  static TurnValidationRule<Turn> all() {
    return isPlayersTurn()
        .and(isMatchOver());
  }
}
