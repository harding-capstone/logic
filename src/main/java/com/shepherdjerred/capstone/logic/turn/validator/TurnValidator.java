package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validator.match.MatchStatusValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.match.PlayerTurnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationBoardCellTypePawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationCoordinateValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationOnePawnSpaceAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationPieceEmptyValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.DestinationTwoPawnSpacesAwayValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.MoveCardinalValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.MoveDiagonalValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.PieceBetweenSourceAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceAndDestinationDifferentValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceBoardCellTypePawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceCoordinateValidValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourcePieceIsPawnValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourcePieceOwnedByPlayerValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.SourceSameAsActualLocationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.movepawn.WallBetweenSourceAndDestinationValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.PlayerHasWallsLeftToPlaceValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallDoesntBlockPawnsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationCoordinatesAreValid;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationCoordinatesAreFreeValidationRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationVertexIsFreeValidatorRule;
import com.shepherdjerred.capstone.logic.turn.validator.placewall.WallPieceLocationVertexIsVertexBoardCellValidatorRule;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// TODO there's probably a better way to apply these rules
public class TurnValidator {

  private final Set<ValidatorRule<Turn>> matchRules;
  private final Set<ValidatorRule<PlaceWallTurn>> placeWallRules;
  private final Set<ValidatorRule<MovePawnTurn>> movePawnRules;
  private final Set<ValidatorRule<MovePawnTurn>> normalMovePawnRules;
  private final Set<ValidatorRule<MovePawnTurn>> straightJumpMovePawnRules;
  private final Set<ValidatorRule<MovePawnTurn>> diagonalJumpMovePawnRules;

  public TurnValidator() {
    matchRules = createMatchTurnRules();
    placeWallRules = createPlaceWallTurnRules();
    movePawnRules = createMovePawnTurnRules();
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
      rules.addAll(movePawnRules);
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

    var results = rules.stream().map(rule -> isTurnValid(turn, match)).collect(Collectors.toSet());
    var result = new TurnValidationResult();
    for (TurnValidationResult r : results) {
      result = TurnValidationResult.combine(result, r);
    }
    return result;
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
    rules.add(new WallDoesntBlockPawnsValidatorRule());
    rules.add(new WallPieceLocationCoordinatesAreFreeValidationRule());
    rules.add(new WallPieceLocationCoordinatesAreValid());
    rules.add(new WallPieceLocationCoordinatesAreWallBoardCellsValidatorRule());
    rules.add(new WallPieceLocationVertexIsFreeValidatorRule());
    rules.add(new WallPieceLocationVertexIsVertexBoardCellValidatorRule());
    return rules;
  }

  private Set<ValidatorRule<MovePawnTurn>> createMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationBoardCellTypePawnValidatorRule());
    rules.add(new DestinationCoordinateValidValidatorRule());
    rules.add(new DestinationPieceEmptyValidatorRule());
    rules.add(new SourceAndDestinationDifferentValidatorRule());
    rules.add(new SourceBoardCellTypePawnValidatorRule());
    rules.add(new SourceCoordinateValidValidatorRule());
    rules.add(new SourcePieceIsPawnValidatorRule());
    rules.add(new SourcePieceOwnedByPlayerValidatorRule());
    rules.add(new SourceSameAsActualLocationValidatorRule());
    return rules;
  }

  private Set<ValidatorRule<MovePawnTurn>> createNormalMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationOnePawnSpaceAwayValidatorRule());
    rules.add(new MoveCardinalValidatorRule());
    rules.add(new WallBetweenSourceAndDestinationValidatorRule());
    return rules;
  }

  // TODO check for walls
  private Set<ValidatorRule<MovePawnTurn>> createStraightJumpMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationTwoPawnSpacesAwayValidatorRule());
    rules.add(new MoveCardinalValidatorRule());
    rules.add(new PieceBetweenSourceAndDestinationValidatorRule());
    return rules;
  }

  // TODO check for walls
  // TODO check for pawn
  private Set<ValidatorRule<MovePawnTurn>> createDiagonalJumpMovePawnTurnRules() {
    Set<ValidatorRule<MovePawnTurn>> rules = new HashSet<>();
    rules.add(new DestinationTwoPawnSpacesAwayValidatorRule());
    rules.add(new MoveDiagonalValidatorRule());
    return rules;
  }
}
