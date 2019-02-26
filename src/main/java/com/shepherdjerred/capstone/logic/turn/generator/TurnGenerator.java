package com.shepherdjerred.capstone.logic.turn.generator;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TurnGenerator {

  public Set<Turn> generateValidTurns(Match match) {
    var allTurns = generateTurns(match);
    return filterInvalidTurns(match, allTurns);
  }

  public Set<Turn> generateInvalidTurns(Match match) {
    var allTurns = generateTurns(match);
    return filterValidTurns(match, allTurns);
  }

  private Set<Turn> generateTurns(Match match) {
    Set<Turn> turns = new HashSet<>();
    var movePawnTurns = generateMovePawnTurns(match);
    var placeWallTurns = generatePlaceWallTurns(match);
    turns.addAll(movePawnTurns);
    turns.addAll(placeWallTurns);
    return turns;
  }

  private Set<Turn> filterInvalidTurns(Match match, Set<Turn> turns) {
    var validator = new TurnValidator();
    return turns.stream()
        .filter(turn -> !validator.isTurnValid(turn, match).isError())
        .collect(Collectors.toSet());
  }

  private Set<Turn> filterValidTurns(Match match, Set<Turn> turns) {
    var validator = new TurnValidator();
    return turns.stream()
        .filter(turn -> validator.isTurnValid(turn, match).isError())
        .collect(Collectors.toSet());
  }

  private Set<MovePawnTurn> generateMovePawnTurns(Match match) {
    Set<MovePawnTurn> turns = new HashSet<>();

    turns.addAll(generateNormalMovePawnTurns(match));
    turns.addAll(generateJumpMovePawnTurns(match));

    return turns;
  }

  private Set<MovePawnTurn> generateNormalMovePawnTurns(Match match) {
    Set<MovePawnTurn> turns = new HashSet<>();

    var player = match.getActivePlayerId();
    var pawnLocation = match.getBoard().getPawnLocation(player);

    var coordLeft = pawnLocation.toLeft(2);
    var coordRight = pawnLocation.toRight(2);
    var coordAbove = pawnLocation.above(2);
    var coordBelow = pawnLocation.below(2);

    var leftMove = new MovePawnTurn(player, MoveType.NORMAL, pawnLocation, coordLeft);
    var rightMove = new MovePawnTurn(player, MoveType.NORMAL, pawnLocation, coordRight);
    var aboveMove = new MovePawnTurn(player, MoveType.NORMAL, pawnLocation, coordAbove);
    var belowMove = new MovePawnTurn(player, MoveType.NORMAL, pawnLocation, coordBelow);

    turns.add(leftMove);
    turns.add(rightMove);
    turns.add(aboveMove);
    turns.add(belowMove);

    return turns;
  }

  // Lots of repeated code :(
  private Set<MovePawnTurn> generateJumpMovePawnTurns(Match match) {
    Set<MovePawnTurn> turns = new HashSet<>();

    var player = match.getActivePlayerId();
    var pawnLocation = match.getBoard().getPawnLocation(player);

    var coordLeft = pawnLocation.toLeft(2);
    var coordRight = pawnLocation.toRight(2);
    var coordAbove = pawnLocation.above(2);
    var coordBelow = pawnLocation.below(2);

    if (match.getBoard().hasPiece(coordLeft)) {
      var diagAbove = coordLeft.above(2);
      var diagBelow = coordLeft.below(2);
      var straight = coordLeft.toLeft(2);

      var diagAboveMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagAbove);
      var diagBelowMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagBelow);
      var straightMove = new MovePawnTurn(player, MoveType.JUMP_STRAIGHT, pawnLocation, straight);

      turns.add(diagAboveMove);
      turns.add(diagBelowMove);
      turns.add(straightMove);
    }

    if (match.getBoard().hasPiece(coordRight)) {
      var diagAbove = coordRight.above(2);
      var diagBelow = coordRight.below(2);
      var straight = coordRight.toRight(2);

      var diagAboveMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagAbove);
      var diagBelowMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagBelow);
      var straightMove = new MovePawnTurn(player, MoveType.JUMP_STRAIGHT, pawnLocation, straight);

      turns.add(diagAboveMove);
      turns.add(diagBelowMove);
      turns.add(straightMove);
    }

    if (match.getBoard().hasPiece(coordAbove)) {
      var diagLeft = coordAbove.toLeft(2);
      var diagRight = coordAbove.toRight(2);
      var straight = coordAbove.above(2);

      var diagLeftMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagLeft);
      var diagRightMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagRight);
      var straightMove = new MovePawnTurn(player, MoveType.JUMP_STRAIGHT, pawnLocation, straight);

      turns.add(diagLeftMove);
      turns.add(diagRightMove);
      turns.add(straightMove);
    }

    if (match.getBoard().hasPiece(coordBelow)) {
      var diagLeft = coordBelow.toLeft(2);
      var diagRight = coordBelow.toRight(2);
      var straight = coordBelow.below(2);

      var diagLeftMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagLeft);
      var diagRightMove = new MovePawnTurn(player, MoveType.JUMP_DIAGONAL, pawnLocation, diagRight);
      var straightMove = new MovePawnTurn(player, MoveType.JUMP_STRAIGHT, pawnLocation, straight);

      turns.add(diagLeftMove);
      turns.add(diagRightMove);
      turns.add(straightMove);
    }

    return turns;
  }

  private Set<PlaceWallTurn> generatePlaceWallTurns(Match match) {
    Set<PlaceWallTurn> turns = new HashSet<>();
    var player = match.getActivePlayerId();

    var gridSize = match.getBoard().getBoardSettings().getGridSize();
    for (int x = 0; x < gridSize - 1; x++) {
      for (int y = 0; y < gridSize - 1; y++) {
        // Vertical walls
        if (x % 2 != 0 && y % 2 == 0) {
          var c1 = new Coordinate(x, y);
          var c2 = new Coordinate(x, y + 2);
          var turn = new PlaceWallTurn(player, c1, c2);
          turns.add(turn);
        }
        // Horizontal walls
        if (x % 2 == 0 && y % 2 != 0) {
          var c1 = new Coordinate(x, y);
          var c2 = new Coordinate(x + 2, y);
          var turn = new PlaceWallTurn(player, c1, c2);
          turns.add(turn);
        }
      }
    }

    return turns;
  }
}
