package com.shepherdjerred.capstone.logic.match.initializer;

import com.google.common.collect.ImmutableMap;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.Player;

public enum DefaultMatchInitializer implements MatchInitializer {
  INSTANCE;

  @Override
  public ImmutableMap<Player, Integer> initializePlayerWalls(MatchSettings matchSettings) {
    int wallsPerPlayer = matchSettings.getWallsPerPlayer();
    if (matchSettings.getPlayerCount() == PlayerCount.TWO) {
      return initializeWallsForTwoPlayers(wallsPerPlayer);
    } else if (matchSettings.getPlayerCount() == PlayerCount.FOUR) {
      return initializeWallsForFourPlayers(wallsPerPlayer);
    } else {
      throw new IllegalStateException("Unknown player count " + matchSettings.getPlayerCount());
    }
  }

  private ImmutableMap<Player, Integer> initializeWallsForTwoPlayers(int wallsPerPlayer) {
    return ImmutableMap.<Player, Integer>builder().
        put(Player.ONE, wallsPerPlayer)
        .put(Player.TWO, wallsPerPlayer)
        .build();
  }

  private ImmutableMap<Player, Integer> initializeWallsForFourPlayers(
      int wallsPerPlayer) {
    return ImmutableMap.<Player, Integer>builder().
        put(Player.ONE, wallsPerPlayer)
        .put(Player.TWO, wallsPerPlayer)
        .put(Player.THREE, wallsPerPlayer)
        .put(Player.FOUR, wallsPerPlayer)
        .build();
  }
}
