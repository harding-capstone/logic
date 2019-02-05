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

  public enum PlayerCount {
    TWO, FOUR
  }
}
