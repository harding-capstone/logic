package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.normal.DestinationIsOnePawnSpaceAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.movepawn.normal.WallIsNotBetweenSourceAndDestinationValidatorRule;
import java.util.HashSet;
import java.util.Set;

public class NormalMovePawnTurnValidator implements TurnValidator<NormalMovePawnTurn> {

  private final Set<ValidatorRule<NormalMovePawnTurn>> rules;

  public NormalMovePawnTurnValidator() {
    rules = new HashSet<>();
    rules.add(new DestinationIsOnePawnSpaceAwayValidatorRule());
    rules.add(new WallIsNotBetweenSourceAndDestinationValidatorRule());
  }

  @Override
  public TurnValidationResult validate(Match match, NormalMovePawnTurn turn) {
    var movePawnValidator = new MovePawnTurnValidator();

    var movePawnResult = movePawnValidator.validate(match, turn);
    var myResult = rules.stream()
        .map(rule -> rule.validate(match, turn))
        .collect(new TurnValidationResultCollector());

    return TurnValidationResult.combine(myResult, movePawnResult);
  }

}
