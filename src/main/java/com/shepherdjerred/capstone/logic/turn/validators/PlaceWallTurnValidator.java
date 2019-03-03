package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validators.rules.ValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.PlayerHasWallsLeftToPlaceValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.WallDoesntBlockPawnsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.WallPieceLocationCoordinatesAreFreeValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.WallPieceLocationCoordinatesAreValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.WallPieceLocationVertexIsFreeValidatorRule;
import java.util.HashSet;
import java.util.Set;

public class PlaceWallTurnValidator implements TurnValidator<PlaceWallTurn> {

  private final Set<ValidatorRule<PlaceWallTurn>> rules;

  public PlaceWallTurnValidator() {
    rules = new HashSet<>();
    rules.add(new PlayerHasWallsLeftToPlaceValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreFreeValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreValidValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule());
    rules.add(new WallPieceLocationVertexIsFreeValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreValidValidatorRule());
  }

  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
    var result = rules.stream()
        .map(rule -> rule.validate(match, turn))
        .collect(new TurnValidationResultCollector());
    if (result.isError()) {
      return result;
    } else {
      var blockResult = new WallDoesntBlockPawnsValidatorRule(new AStarBoardSearch(),
          new PlayerGoals()).validate(match, turn);
      return TurnValidationResult.combine(result, blockResult);
    }
  }

}
