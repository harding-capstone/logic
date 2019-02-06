package com.shepherdjerred.capstone.logic.turn.enactor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.match.MatchState;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum PlaceWallTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given boardState state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the boardState
   * @param matchState The initial matchState state
   * @return The initial matchState state transformed by the turn
   */
  @Override
  public MatchState enactTurn(Turn turn, MatchState matchState) {
    if (turn instanceof PlaceWallTurn) {
      return enactPlaceWallTurn((PlaceWallTurn) turn, matchState);
    } else {
      throw new IllegalArgumentException("Turn is not a PlaceWallTurn " + turn);
    }
  }

  private MatchState enactPlaceWallTurn(PlaceWallTurn turn, MatchState matchState) {
    var board = matchState.getBoardState();
    var newBoard = board.setWallPiece(turn.getCauser(),
        turn.getFirstCoordinate(),
        turn.getSecondCoordinate());
    var updatedWalls = updatePlayerWalls(turn, matchState);
    return MatchState.builder()
        .boardState(newBoard)
        .matchSettings(matchState.getMatchSettings())
        .turnEnactorFactory(matchState.getTurnEnactorFactory())
        .turnValidatorFactory(matchState.getTurnValidatorFactory())
        .currentPlayerTurn(matchState.getNextPlayer())
        .playerWalls(updatedWalls)
        .build();
  }

  // TODO this could be done better
  private ImmutableMap<Player, Integer> updatePlayerWalls(PlaceWallTurn turn, MatchState matchState) {
    var target = turn.getCauser();
    var oldWallValue = matchState.getRemainingWallCount(target);
    var updatedPlayerWalls = ImmutableMap.<Player, Integer>builder()
        .put(turn.getCauser(), oldWallValue - 1).build();
    return ImmutableMap.<Player, Integer>builder()
        .putAll(updatedPlayerWalls)
        .putAll(Maps.difference(matchState.getPlayerWalls(), updatedPlayerWalls).entriesOnlyOnLeft())
        .build();
  }
}
