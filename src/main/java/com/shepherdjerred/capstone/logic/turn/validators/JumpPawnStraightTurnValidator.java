package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.JumpPawnStraightTurn;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.straight.StraightPivotIsValidValidatorRule;
import java.util.HashSet;
import java.util.Set;

public class JumpPawnStraightTurnValidator implements TurnValidator<JumpPawnStraightTurn> {

  private final Set<ValidatorRule<JumpPawnStraightTurn>> rules;

  public JumpPawnStraightTurnValidator() {
    rules = new HashSet<>();
    rules.add(new StraightPivotIsValidValidatorRule());
  }

  @Override
  public TurnValidationResult validate(Match match, JumpPawnStraightTurn turn) {
    var movePawnValidator = new MovePawnTurnValidator();
    var jumpPawnValidator = new JumpPawnTurnValidator();

    var movePawnResult = movePawnValidator.validate(match, turn);
    var jumpPawnResult = jumpPawnValidator.validate(match, turn);
    var myResult = rules.stream()
        .map(rule -> rule.validate(match, turn))
        .collect(new TurnValidationResultCollector());

    return TurnValidationResult.combine(myResult,
        TurnValidationResult.combine(movePawnResult, jumpPawnResult));
  }

}
