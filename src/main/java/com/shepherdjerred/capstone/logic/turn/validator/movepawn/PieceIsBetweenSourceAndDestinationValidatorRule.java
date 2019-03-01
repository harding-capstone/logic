package com.shepherdjerred.capstone.logic.turn.validator.movepawn;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;

public class PieceIsBetweenSourceAndDestinationValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var source = turn.getSource();
    var destination = turn.getDestination();
    var midpoint = Coordinate.calculateMidpoint(source, destination);

    if (match.getBoard().isEmpty(midpoint)) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.PIECE_NOT_BETWEEN_SOURCE_AND_DESTINATION);
    }
  }
}
