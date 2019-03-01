package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validator.match.MatchStatusValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.match.PlayerTurnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationBoardCellTypeIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationCoordinateIsValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationIsOnePawnSpaceAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationIsTwoPawnSpacesAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationPieceIsEmptyValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.MoveIsCardinalValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.MoveIsDiagonalValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.PieceIsBetweenSourceAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceAndDestinationAreDifferentValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceBoardCellTypeIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceCoordinateIsValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourcePieceIsOwnedByCauserValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourcePieceTypeIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.TurnSourceIsSameAsActualLocationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.WallBetweenIsNotSourceAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.PlayerHasWallsLeftToPlaceValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallDoesntBlockPawnsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationCoordinatesAreFreeValidationRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationCoordinatesAreValid;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationVertexIsFreeValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationVertexIsVertexBoardCellValidatorRule;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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

    var results = rules.stream()
        .map(rule -> {
          var result = rule.validate(match, turn);
//          log.info(rule + " " + result);
          return result;
        })
        .collect(Collectors.toSet());

    var combinedResult = new TurnValidationResult();
    for (TurnValidationResult result : results) {
      combinedResult = TurnValidationResult.combine(combinedResult, result);
    }
//    log.info("=== RESULT: " + combinedResult);
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
    rules.add(new WallDoesntBlockPawnsValidatorRule(new AStarBoardSearch(), new PlayerGoals()));
    rules.add(new WallPieceLocationCoordinatesAreFreeValidationRule());
    rules.add(new WallPieceLocationCoordinatesAreValid());
    rules.add(new WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule());
    rules.add(new WallPieceLocationVertexIsFreeValidatorRule());
    rules.add(new WallPieceLocationVertexIsVertexBoardCellValidatorRule());
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
  // TODO check for pawn
  private Set<ValidatorRule<MovePawnTurn>> createDiagonalJumpMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationIsTwoPawnSpacesAwayValidatorRule());
    rules.add(new MoveIsDiagonalValidatorRule());
    return rules;
  }
}
