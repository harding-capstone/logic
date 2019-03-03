package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.JumpPawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.rules.movepawn.jump.DestinationIsTwoPawnSpacesAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.rules.movepawn.jump.PivotIsSameAsActualLocationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.rules.movepawn.jump.WallIsNotBetweenPivotAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.rules.movepawn.jump.WallIsNotBetweenSourceAndPivotValidatorRule;
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
    var result = new TurnValidationResult();
    for (ValidatorRule<JumpPawnTurn> rule : rules) {
      var ruleResult = rule.validate(match, turn);
      result = TurnValidationResult.combine(result, ruleResult);
    }
    return result;
  }

}
