package com.shepherdjerred.capstone.logic.turn.validators.movepawn;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.ValidatorRule;
import lombok.ToString;

@ToString
public class SourcePieceIsOwnedByCauserValidatorRule implements ValidatorRule<MovePawnTurn> {

  @Override
  public TurnValidationResult validate(Match match, MovePawnTurn turn) {
    var source = turn.getSource();
    var mover = turn.getCauser();
    var sourcePieceOwner = match.getBoard().getPiece(source).getOwner();

    if (mover == sourcePieceOwner) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.SOURCE_PIECE_NOT_OWNED_BY_PLAYER);
    }
  }
}
