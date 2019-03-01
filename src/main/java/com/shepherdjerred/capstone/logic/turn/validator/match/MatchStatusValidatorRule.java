package com.shepherdjerred.capstone.logic.turn.validator.match;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class MatchStatusValidatorRule implements ValidatorRule<Turn> {

  @Override
  public TurnValidationResult validate(Match match, Turn turn) {
    if (match.getMatchStatus().getStatus() == Status.IN_PROGRESS) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.GAME_ALREADY_OVER);
    }
  }
}
