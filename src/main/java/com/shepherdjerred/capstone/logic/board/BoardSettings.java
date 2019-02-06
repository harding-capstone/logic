package com.shepherdjerred.capstone.logic.board;

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
    this.gridSize = boardSize * 2 - 1;
  }

  public enum PlayerCount {
    TWO, FOUR
  }
}
