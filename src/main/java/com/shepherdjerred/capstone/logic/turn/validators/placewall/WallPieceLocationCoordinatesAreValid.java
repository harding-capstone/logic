package com.shepherdjerred.capstone.logic.turn.validators.placewall;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validators.ValidatorRule;

public class WallPieceLocationCoordinatesAreValid implements ValidatorRule<PlaceWallTurn> {

  // TODO
  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
    return new TurnValidationResult();
  }
}
