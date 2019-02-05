package com.shepherdjerred.capstone.logic;

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

  private PlayerCount playerCount;
  private BoardSettings boardSettings;

  public enum PlayerCount {
    TWO, FOUR
  }
}
