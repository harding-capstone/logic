package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class BoardSettings {

  private final int boardSize;
  private final int gridSize;
  private final PlayerCount playerCount;

  public BoardSettings(int boardSize, PlayerCount playerCount) {
    this.boardSize = boardSize;
    this.gridSize = boardSize * 2 - 1;
    this.playerCount = playerCount;

    if (boardSize % 2 != 1) {
      throw new IllegalArgumentException("Board gridSize must be odd");
    }
  }
}
