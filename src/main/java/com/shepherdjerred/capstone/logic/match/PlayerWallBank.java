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
public final class PlayerWallBank {

  public final Map<Player, Integer> playerWalls;

  public static PlayerWallBank createWallPool(PlayerCount playerCount, int numberOfWalls) {
    Map<Player, Integer> walls = new HashMap<>();
    Set<Player> players = new HashSet<>();
    players.add(Player.ONE);
    players.add(Player.TWO);
    if (playerCount == PlayerCount.FOUR) {
      players.add(Player.THREE);
      players.add(Player.FOUR);
    }
    players.forEach(player -> walls.put(player, numberOfWalls));
    return new PlayerWallBank(walls);
  }

  private PlayerWallBank(Map<Player, Integer> playerWalls) {
    this.playerWalls = playerWalls;
  }

  public int getWallsLeft(Player player) {
    if (playerWalls.containsKey(player)) {
      return playerWalls.get(player);
    } else {
      throw new IllegalArgumentException(player.toString());
    }
  }

  public PlayerWallBank takeWall(Player player) {
    if (playerWalls.containsKey(player)) {
      Map<Player, Integer> newWallCounts = new HashMap<>(playerWalls);
      var currentWallsLeft = getWallsLeft(player) - 1;
      newWallCounts.put(player, currentWallsLeft);
      return new PlayerWallBank(newWallCounts);
    } else {
      throw new IllegalArgumentException(player.toString());
    }
  }
}
