package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.function.BiFunction;

public interface TurnValidationRule<T extends Turn> extends
    BiFunction<T, Match, TurnValidationResult> {

  default TurnValidationRule<T> and(TurnValidationRule<T> other) {
    return (turn, match) -> TurnValidationResult.combine(this.apply(turn, match),
        other.apply(turn, match));
  }
}
