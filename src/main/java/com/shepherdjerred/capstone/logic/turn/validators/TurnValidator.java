package com.shepherdjerred.capstone.logic.turn.validators;

import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.match.MatchStatusValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.match.PlayerTurnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.DestinationBoardCellTypeIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.DestinationCoordinateIsValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.DestinationIsOnePawnSpaceAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.DestinationIsTwoPawnSpacesAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.DestinationPieceIsEmptyValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.MoveIsCardinalValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.MoveIsDiagonalValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.PieceIsBetweenSourceAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.SourceAndDestinationAreDifferentValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.SourceBoardCellTypeIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.SourceCoordinateIsValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.SourcePieceIsOwnedByCauserValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.SourcePieceTypeIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.TurnSourceIsSameAsActualLocationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.movepawn.WallBetweenIsNotSourceAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.PlayerHasWallsLeftToPlaceValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.WallDoesntBlockPawnsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.WallPieceLocationCoordinatesAreFreeValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.WallPieceLocationCoordinatesAreValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.WallPieceLocationVertexIsFreeValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validators.placewall.WallPieceLocationVertexIsVertexBoardCellValidatorRule;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

// TODO there's probably a better way to apply these rules
@Log4j2
public class TurnValidator {

  private final Set<ValidatorRule<Turn>> matchRules;
  private final Set<ValidatorRule<PlaceWallTurn>> placeWallRules;
  private final Set<ValidatorRule<MovePawnTurn>> genericMovePawnRules;
  private final Set<ValidatorRule<MovePawnTurn>> normalMovePawnRules;
  private final Set<ValidatorRule<MovePawnTurn>> straightJumpMovePawnRules;
  private final Set<ValidatorRule<MovePawnTurn>> diagonalJumpMovePawnRules;

  public TurnValidator() {
    matchRules = createMatchTurnRules();
    placeWallRules = createPlaceWallTurnRules();
    genericMovePawnRules = createMovePawnTurnRules();
    normalMovePawnRules = createNormalMovePawnTurnRules();
    straightJumpMovePawnRules = createStraightJumpMovePawnTurnRules();
    diagonalJumpMovePawnRules = createDiagonalJumpMovePawnTurnRules();
  }

  public TurnValidationResult isTurnValid(Turn turn, Match match) {
    Set<ValidatorRule> rules = new HashSet<>(matchRules);

    if (turn instanceof PlaceWallTurn) {
      rules.addAll(placeWallRules);
    } else if (turn instanceof MovePawnTurn) {
      var movePawnTurn = (MovePawnTurn) turn;
      var type = movePawnTurn.getMoveType();
      rules.addAll(genericMovePawnRules);
      if (type == MoveType.NORMAL) {
        rules.addAll(normalMovePawnRules);
      } else if (type == MoveType.JUMP_STRAIGHT) {
        rules.addAll(straightJumpMovePawnRules);
      } else if (type == MoveType.JUMP_DIAGONAL) {
        rules.addAll(diagonalJumpMovePawnRules);
      } else {
        throw new UnsupportedOperationException();
      }
    } else {
      throw new UnsupportedOperationException();
    }

    var combinedResult = new TurnValidationResult();
    for (ValidatorRule rule : rules) {
      TurnValidationResult result;
      try {
        result = rule.validate(match, turn);
      } catch (Exception e) {
        log.error("Error running turn validator: " + rule + " " + turn, e);
        result = new TurnValidationResult(ErrorMessage.VALIDATOR_FAILED);
      }
      combinedResult = TurnValidationResult.combine(combinedResult, result);
      // TODO failfast toggle
      if (combinedResult.isError()) {
        break;
      }
    }

    if (turn instanceof PlaceWallTurn && !combinedResult.isError()) {
      var finalThing = new WallDoesntBlockPawnsValidatorRule(new AStarBoardSearch(),
          new PlayerGoals()).validate(match, (PlaceWallTurn) turn);
      combinedResult = TurnValidationResult.combine(combinedResult, finalThing);
    }

    return combinedResult;
  }

  private Set<ValidatorRule<Turn>> createMatchTurnRules() {
    Set<ValidatorRule<Turn>> rules = new HashSet<>();
    rules.add(new MatchStatusValidatorRule());
    rules.add(new PlayerTurnValidatorRule());
    return rules;
  }

  private Set<ValidatorRule<PlaceWallTurn>> createPlaceWallTurnRules() {
    Set<ValidatorRule<PlaceWallTurn>> rules = new HashSet<>();
    rules.add(new PlayerHasWallsLeftToPlaceValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreFreeValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreValidValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule());
    rules.add(new WallPieceLocationVertexIsFreeValidatorRule());
    rules.add(new WallPieceLocationVertexIsVertexBoardCellValidatorRule());
//    rules.add(new WallDoesntBlockPawnsValidatorRule(new AStarBoardSearch(), new PlayerGoals()));
    return rules;
  }

  private Set<ValidatorRule<MovePawnTurn>> createMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationBoardCellTypeIsPawnValidatorRule());
    rules.add(new DestinationCoordinateIsValidValidatorRule());
    rules.add(new DestinationPieceIsEmptyValidatorRule());
    rules.add(new SourceAndDestinationAreDifferentValidatorRule());
    rules.add(new SourceBoardCellTypeIsPawnValidatorRule());
    rules.add(new SourceCoordinateIsValidValidatorRule());
    rules.add(new SourcePieceTypeIsPawnValidatorRule());
    rules.add(new SourcePieceIsOwnedByCauserValidatorRule());
    rules.add(new TurnSourceIsSameAsActualLocationValidatorRule());
    return rules;
  }

  private Set<ValidatorRule<MovePawnTurn>> createNormalMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationIsOnePawnSpaceAwayValidatorRule());
    rules.add(new MoveIsCardinalValidatorRule());
    rules.add(new WallBetweenIsNotSourceAndDestinationValidatorRule());
    return rules;
  }

  // TODO check for walls
  private Set<ValidatorRule<MovePawnTurn>> createStraightJumpMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationIsTwoPawnSpacesAwayValidatorRule());
    rules.add(new MoveIsCardinalValidatorRule());
    rules.add(new PieceIsBetweenSourceAndDestinationValidatorRule());
    return rules;
  }

  // TODO check for walls
  // TODO check for pawn pivot
  private Set<ValidatorRule<MovePawnTurn>> createDiagonalJumpMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationIsTwoPawnSpacesAwayValidatorRule());
    rules.add(new MoveIsDiagonalValidatorRule());
    return rules;
  }
}
