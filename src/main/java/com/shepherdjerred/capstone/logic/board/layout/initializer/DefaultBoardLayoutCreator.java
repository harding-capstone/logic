package com.shepherdjerred.capstone.logic.board.layout.initializer;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;

public enum DefaultBoardLayoutCreator implements BoardLayoutCreator {
  INSTANCE;

  /**
   * Creates a matrix of BoardLayout
   *
   * @param boardSettings Settings to use when creating the boardState
   * @return A matrix of BoardLayout based on the boardState settings
   */
  public BoardCell[][] createBoardCells(BoardSettings boardSettings) {

    if (boardSettings.getBoardSize() % 2 != 1) {
      throw new IllegalArgumentException("BoardLayout size must be an odd number");
    }

    var gridSize = boardSettings.getGridSize();
    var boardCells = new BoardCell[gridSize][gridSize];

    for (int x = 0; x < gridSize; x++) {
      for (int y = 0; y < gridSize; y++) {
        boardCells[x][y] = createBoardCell(new Coordinate(x, y));
      }
    }

    return boardCells;
  }

  /**
   * Creates a single BoardCell
   *
   * @param coordinate The coordinate that the layout will exist
   * @return The BoardCell that should exist a the coordinate
   */
  private BoardCell createBoardCell(Coordinate coordinate) {
    if (shouldBeNullCell(coordinate)) {
      return BoardCell.NULL;
    } else if (shouldBePawnCell(coordinate)) {
      return BoardCell.PAWN;
    } else if (shouldBeWallCell(coordinate)) {
      return BoardCell.WALL;
    } else {
      throw new IllegalStateException("Couldn't get layout type for " + coordinate);
    }
  }

  /**
   * Checks if a layout should be null
   *
   * @param coordinate The coordinate to check
   * @return True if the layout should be null, or false otherwise
   */
  private boolean shouldBeNullCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 != 0
        && y % 2 != 0;
  }

  /**
   * Checks if a layout should be a pawn layout
   *
   * @param coordinate The coordinate to check
   * @return True if the layout should be a pawn layout, or false otherwise
   */
  private boolean shouldBePawnCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 == 0
        && y % 2 == 0;
  }

  /**
   * Checks if a layout should be a wall layout
   *
   * @param coordinate The coordinate to check
   * @return True if the layout should be a wall layout, or false otherwise
   */
  private boolean shouldBeWallCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 != 0
        && y % 2 == 0
        || x % 2 == 0
        && y % 2 != 0;
  }
}
