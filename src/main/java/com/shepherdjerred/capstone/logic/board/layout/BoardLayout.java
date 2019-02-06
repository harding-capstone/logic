package com.shepherdjerred.capstone.logic.board.layout;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class BoardLayout {

  @Getter
  private final BoardSettings boardSettings;
  private final BoardCell[][] boardCells;

  /**
   * Constructor for a new board
   *
   * @param boardSettings Settings for the board
   */
  public BoardLayout(BoardSettings boardSettings) {
    this.boardSettings = boardSettings;
    boardCells = createBoardCells(boardSettings);
  }

  /**
   * Returns the BoardCell at a given Coordinate
   *
   * @param coordinate Coordinate of the BoardCell to get
   * @return The BoardCell at the Coordinate
   */
  public BoardCell getCell(Coordinate coordinate) {
    if (isCoordinateInvalid(coordinate)) {
      throw new CoordinateOutOfBoundsException(coordinate);
    }
    return boardCells[coordinate.getX()][coordinate.getY()];
  }

  public boolean isCoordinateInvalid(Coordinate coordinate) {
    return !isCoordinateValid(coordinate);
  }

  public boolean isCoordinateValid(Coordinate coordinate) {
    var gridSize = boardSettings.getGridSize();
    var x = coordinate.getX();
    var y = coordinate.getY();
    return x >= 0
        && x <= gridSize - 1
        && y >= 0
        && y <= gridSize - 1;
  }

  /**
   * Creates a matrix of BoardCell
   *
   * @param boardSettings Settings to use when creating the board
   * @return A matrix of BoardCell based on the board settings
   */
  private BoardCell[][] createBoardCells(BoardSettings boardSettings) {

    if (boardSettings.getBoardSize() % 2 != 1) {
      throw new IllegalArgumentException("Board size must be an odd number");
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
   * @param coordinate The coordinate at which that the boardcell will exist
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
      throw new IllegalStateException("Couldn't get board type for " + coordinate);
    }
  }

  /**
   * Checks if a boardcell should be null
   *
   * @param coordinate The coordinate to check
   * @return True if the boardcell should be null, or false otherwise
   */
  private boolean shouldBeNullCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 != 0
        && y % 2 != 0;
  }

  /**
   * Checks if a boardcell should be a pawn boardcell
   *
   * @param coordinate The coordinate to check
   * @return True if the boardcell should be a pawn boardcell, or false otherwise
   */
  private boolean shouldBePawnCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 == 0
        && y % 2 == 0;
  }

  /**
   * Checks if a boardcell should be a wall boardcell
   *
   * @param coordinate The coordinate to check
   * @return True if the boardcell should be a wall boardcell, or false otherwise
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
