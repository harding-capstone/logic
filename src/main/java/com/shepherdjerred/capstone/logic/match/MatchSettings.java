package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class MatchSettings {

  private final int wallsPerPlayer;
  private final PlayerId startingPlayerId;
  private final BoardSettings boardSettings;

  public MatchSettings(int wallsPerPlayer,
      PlayerId startingPlayerId,
      BoardSettings boardSettings) {

    if (boardSettings.getPlayerCount() == PlayerCount.TWO) {
      if (startingPlayerId.toInt() > boardSettings.getPlayerCount().toInt()) {
        throw new IllegalArgumentException("Starting player cannot be greater than player count");
      }
    }

    this.wallsPerPlayer = wallsPerPlayer;
    this.startingPlayerId = startingPlayerId;
    this.boardSettings = boardSettings;
  }

  @ToString
  public enum PlayerCount {
    TWO, FOUR;

    public int toInt() {
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
