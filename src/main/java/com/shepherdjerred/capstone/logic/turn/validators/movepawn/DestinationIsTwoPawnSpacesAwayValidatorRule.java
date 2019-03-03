package com.shepherdjerred.capstone.logic.turn.validators.movepawn;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.ValidatorRule;

public class DestinationIsTwoPawnSpacesAwayValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var dist = Coordinate.calculateManhattanDistance(turn.getSource(), turn.getDestination());

    // We check if the distance equals four because wall cells count in the calculation
    if (dist == 4) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.MOVE_NOT_TWO_SPACES_AWAY);
    }
  }
}
