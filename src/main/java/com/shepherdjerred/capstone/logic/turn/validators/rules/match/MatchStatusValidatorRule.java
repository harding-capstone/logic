package com.shepherdjerred.capstone.logic.turn.validators.rules.match;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;

public class MatchStatusValidatorRule implements ValidatorRule<Turn> {

  @Override
  public TurnValidationResult validate(Match match, Turn turn) {
    if (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.MATCH_ALREADY_OVER);
    }
  }
}