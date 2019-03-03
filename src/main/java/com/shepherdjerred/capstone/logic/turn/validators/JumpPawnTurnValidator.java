package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.JumpPawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.DestinationIsTwoPawnSpacesAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.PivotIsSameAsActualLocationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.WallIsNotBetweenPivotAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.WallIsNotBetweenSourceAndPivotValidatorRule;
import java.util.HashSet;
import java.util.Set;

public class JumpPawnTurnValidator implements TurnValidator<JumpPawnTurn> {

  private final Set<ValidatorRule<JumpPawnTurn>> rules;

  public JumpPawnTurnValidator() {
    rules = new HashSet<>();
    rules.add(new DestinationIsTwoPawnSpacesAwayValidatorRule());
    rules.add(new PivotIsSameAsActualLocationValidatorRule());
    rules.add(new WallIsNotBetweenPivotAndDestinationValidatorRule());
    rules.add(new WallIsNotBetweenSourceAndPivotValidatorRule());
  }

  @Override
  public TurnValidationResult validate(Match match, JumpPawnTurn turn) {
    return rules.stream()
        .map(rule -> rule.validate(match, turn))
        .collect(new TurnValidationResultCollector());
  }

}
