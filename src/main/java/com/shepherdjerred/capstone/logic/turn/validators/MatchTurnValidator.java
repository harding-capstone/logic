package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.match.MatchStatusValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.match.PlayerTurnValidatorRule;
import java.util.HashSet;
import java.util.Set;

public class MatchTurnValidator implements TurnValidator<Turn> {

  private final Set<ValidatorRule<Turn>> rules;

  public MatchTurnValidator() {
    rules = new HashSet<>();
    rules.add(new MatchStatusValidatorRule());
    rules.add(new PlayerTurnValidatorRule());
  }

  @Override
  public TurnValidationResult validate(Match match, Turn turn) {
    return rules.stream()
        .map(rule -> rule.validate(match, turn))
        .collect(new TurnValidationResultCollector());
  }
}
