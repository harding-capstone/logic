package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.player.Player;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class MatchSettings {

  private final int wallsPerPlayer;
  private final Player startingPlayer;
  private final BoardSettings boardSettings;

  public MatchSettings(int wallsPerPlayer,
      Player startingPlayer,
      BoardSettings boardSettings) {

    if (boardSettings.getPlayerCount() == PlayerCount.TWO) {
      if (startingPlayer.toInt() > boardSettings.getPlayerCount().toInt()) {
        throw new IllegalArgumentException("Starting player cannot be greater than player count");
      }
    }

    this.wallsPerPlayer = wallsPerPlayer;
    this.startingPlayer = startingPlayer;
    this.boardSettings = boardSettings;
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
