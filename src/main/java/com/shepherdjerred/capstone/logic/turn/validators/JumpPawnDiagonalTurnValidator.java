package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.JumpPawnDiagonalTurn;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.diagonal.DiagonalPivotIsValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.jump.diagonal.MoveIsDiagonalValidatorRule;
import java.util.HashSet;
import java.util.Set;

public class JumpPawnDiagonalTurnValidator implements TurnValidator<JumpPawnDiagonalTurn> {

  private final Set<ValidatorRule<JumpPawnDiagonalTurn>> rules;

  public JumpPawnDiagonalTurnValidator() {
    rules = new HashSet<>();
    rules.add(new DiagonalPivotIsValidValidatorRule());
    rules.add(new MoveIsDiagonalValidatorRule());
  }

  @Override
  public TurnValidationResult validate(Match match, JumpPawnDiagonalTurn turn) {
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
