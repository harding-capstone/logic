package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.player.Player;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class MatchSettings {

  private final int wallsPerPlayer;
  private final PlayerCount playerCount;
  private final Player startingPlayer;

  public MatchSettings(
      int wallsPerPlayer,
      PlayerCount playerCount,
      Player startingPlayer) {

    if (playerCount == PlayerCount.TWO) {
      if (startingPlayer.toInt() > playerCount.toInt()) {
        throw new IllegalArgumentException("Starting player cannot be greater than player count");
      }
    }

    this.playerCount = playerCount;
    this.wallsPerPlayer = wallsPerPlayer;
    this.startingPlayer = startingPlayer;
  }

  @ToString
  public enum PlayerCount {
    TWO, FOUR;

    int toInt() {
      switch (this) {
        case TWO:
          return 2;
        case FOUR:
          return 4;
        default:
          throw new IllegalStateException(this.toString());
      }
    }
  }
}
