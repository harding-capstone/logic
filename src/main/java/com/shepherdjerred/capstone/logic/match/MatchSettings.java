package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class MatchSettings {

  private final PlayerCount playerCount;
  private final int wallsPerPlayer;
  private final BoardSettings boardSettings;
  private final Player startingPlayer;

  public MatchSettings(
      PlayerCount playerCount,
      int wallsPerPlayer,
      BoardSettings boardSettings,
      Player startingPlayer) {

    if (playerCount == PlayerCount.TWO) {
      if (startingPlayer.toInt() > playerCount.toInt()) {
        throw new IllegalArgumentException("Starting player cannot be greater than player count");
      }
    }

    this.playerCount = playerCount;
    this.wallsPerPlayer = wallsPerPlayer;
    this.boardSettings = boardSettings;
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
