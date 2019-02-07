package com.shepherdjerred.capstone.logic.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class BoardSettings {

  private final int boardSize;
  private final int gridSize;

  public BoardSettings(int boardSize) {
    this.boardSize = boardSize;
    this.gridSize = boardSize * 2 - 1;

    if (boardSize % 2 != 1) {
      throw new IllegalArgumentException("Board getSize must be odd");
    }
  }
}
