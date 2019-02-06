package com.shepherdjerred.capstone.logic.turn.enactor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum PlaceWallTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given board state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the board
   * @param match The initial match state
   * @return The initial match state transformed by the turn
   */
  @Override
  public Match enactTurn(Turn turn, Match match) {
    if (turn instanceof PlaceWallTurn) {
      return enactPlaceWallTurn((PlaceWallTurn) turn, match);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  private Match enactPlaceWallTurn(PlaceWallTurn turn, Match match) {
    var board = match.getBoard();
    var newBoard = board.setWallPiece(turn.getCauser(),
        turn.getFirstCoordinate(),
        turn.getSecondCoordinate());
    var updatedWalls = updatePlayerWalls(turn, match);
    return Match.builder()
        .board(newBoard)
        .matchSettings(match.getMatchSettings())
        .turnEnactorFactory(match.getTurnEnactorFactory())
        .turnValidatorFactory(match.getTurnValidatorFactory())
        .currentPlayerTurn(match.getNextPlayer())
        .playerWalls(updatedWalls)
        .build();
  }

  // TODO this could be done better
  private ImmutableMap<Player, Integer> updatePlayerWalls(PlaceWallTurn turn, Match match) {
    var target = turn.getCauser();
    var oldWallValue = match.getRemainingWallCount(target);
    var updatedPlayerWalls = ImmutableMap.<Player, Integer>builder()
        .put(turn.getCauser(), oldWallValue - 1).build();
    return ImmutableMap.<Player, Integer>builder()
        .putAll(updatedPlayerWalls)
        .putAll(Maps.difference(match.getPlayerWalls(), updatedPlayerWalls).entriesOnlyOnLeft())
        .build();
  }
}
