package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class MatchSettings {

  private final PlayerCount playerCount;
  private final int wallsPerPlayer;
  private final BoardSettings boardSettings;
  private final Player startingPlayer;

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
