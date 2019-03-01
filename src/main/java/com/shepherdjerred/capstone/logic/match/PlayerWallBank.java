package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class PlayerWallBank {

  public final Map<PlayerId, Integer> playerWalls;

  public static PlayerWallBank from(PlayerCount playerCount, int numberOfWalls) {
    Map<PlayerId, Integer> walls = new HashMap<>();
    Set<PlayerId> playerIds = new HashSet<>();
    playerIds.add(PlayerId.ONE);
    playerIds.add(PlayerId.TWO);
    if (playerCount == PlayerCount.FOUR) {
      playerIds.add(PlayerId.THREE);
      playerIds.add(PlayerId.FOUR);
    }
    playerIds.forEach(player -> walls.put(player, numberOfWalls));
    return new PlayerWallBank(walls);
  }

  private PlayerWallBank(Map<PlayerId, Integer> playerWalls) {
    this.playerWalls = playerWalls;
  }

  public int getWallsLeft(PlayerId playerId) {
    if (playerWalls.containsKey(playerId)) {
      return playerWalls.get(playerId);
    } else {
      throw new IllegalArgumentException(playerId.toString());
    }
  }

  public PlayerWallBank takeWall(PlayerId playerId) {
    if (playerWalls.containsKey(playerId)) {
      Map<PlayerId, Integer> newWallCounts = new HashMap<>(playerWalls);
      var currentWallsLeft = getWallsLeft(playerId) - 1;
      newWallCounts.put(playerId, currentWallsLeft);
      return new PlayerWallBank(newWallCounts);
    } else {
      throw new IllegalArgumentException(playerId.toString());
    }
  }
}
