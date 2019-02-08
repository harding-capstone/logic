package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class WallPool {

  public final Map<Player, Integer> playerWalls;

  public static WallPool createWallPool(PlayerCount playerCount, int numberOfWalls) {
    Map<Player, Integer> walls = new HashMap<>();
    Set<Player> players = new HashSet<>();
    players.add(Player.ONE);
    players.add(Player.TWO);
    if (playerCount == PlayerCount.FOUR) {
      players.add(Player.THREE);
      players.add(Player.FOUR);
    }
    players.forEach(player -> walls.put(player, numberOfWalls));
    return new WallPool(walls);
  }

  private WallPool(Map<Player, Integer> playerWalls) {
    this.playerWalls = playerWalls;
  }

  public int getWallsLeft(Player player) {
    if (playerWalls.containsKey(player)) {
      return playerWalls.get(player);
    } else {
      throw new IllegalArgumentException(player.toString());
    }
  }

  public WallPool takeWall(Player player) {
    if (playerWalls.containsKey(player)) {
      Map<Player, Integer> newWallCounts = new HashMap<>(playerWalls);
      var currentWallsLeft = getWallsLeft(player) - 1;
      newWallCounts.put(player, currentWallsLeft);
      return new WallPool(newWallCounts);
    } else {
      throw new IllegalArgumentException(player.toString());
    }
  }
}
