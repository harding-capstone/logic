package com.shepherdjerred.capstone.logic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class BoardSettings {
  private final PlayerCount playerCount;
  private final int boardSize;
  private final int gridSize;

  public BoardSettings(PlayerCount playerCount, int boardSize) {
    this.playerCount = playerCount;
    this.boardSize = boardSize;
    this.gridSize = boardSize * 2;
  }

  public int getGridSize() {
    return gridSize;
  }

  public enum PlayerCount {
      TWO, FOUR
  }
}
