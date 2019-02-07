package com.shepherdjerred.capstone.logic.board.layout;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the Quoridor board layout. In a standard 9x9 game of Quoridor, the boardCells array
 * will be 17 * 17. The boardCells array stores both the pawn spaces and grooves where walls are
 * placed. Along with the wall and pawn spaces in the boardCells array, there are Null cells which
 * represent areas in the array where no element exists on the Quoridor board. These BoardCells
 * should be ignored.
 */
//@ToString
@EqualsAndHashCode
public final class BoardLayout {

  @Getter
  private final BoardSettings boardSettings;
  private final BoardCell[][] boardCells;


  public static BoardLayout fromBoardSettings(BoardSettings boardSettings) {
    var boardCells = createBoardCells(boardSettings);
    return new BoardLayout(boardSettings, boardCells);
  }

  /**
   * Constructor for a new BoardLayout. This constructor will create a BoardLayout based on the
   * BoardSettings object it receives.
   *
   * @param boardSettings Settings to use when creating the BoardLayout
   */
  private BoardLayout(BoardSettings boardSettings, BoardCell[][] boardCells) {
    this.boardSettings = boardSettings;
    this.boardCells = boardCells;
  }

  /**
   * Returns the BoardCell at a given Coordinate
   *
   * @param coordinate Coordinate of the BoardCell to get
   * @return The BoardCell at the Coordinate
   */
  public BoardCell getBoardCell(Coordinate coordinate) {
    if (isCoordinateInvalid(coordinate)) {
      throw new CoordinateOutOfBoundsException(coordinate);
    }
    return boardCells[coordinate.getX()][coordinate.getY()];
  }

  public boolean isPawnBoardCell(Coordinate coordinate) {
    return getBoardCell(coordinate) == BoardCell.PAWN;
  }

  public boolean isWallBoardCell(Coordinate coordinate) {
    return getBoardCell(coordinate) == BoardCell.WALL;
  }

  /**
   * Checks if a Coordinate is invalid on this board
   *
   * @param coordinate The Coordinate to check
   * @return True if the Coordinate is invalid, false otherwise
   */
  public boolean isCoordinateInvalid(Coordinate coordinate) {
    return !isCoordinateValid(coordinate);
  }

  /**
   * Checks if a coordinate is valid on this board
   *
   * @param coordinate The Coordinate to check
   * @return True if the Coordinate is valid, false otherwise
   */
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
   * Creates a matrix of BoardCells. Used for constructor initialization.
   *
   * @param boardSettings Settings to use when creating the board
   * @return A matrix of BoardCell based on the board settings
   */
  private static BoardCell[][] createBoardCells(BoardSettings boardSettings) {
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
   * @param coordinate The Coordinate at which that the BoardCell will exist
   * @return The BoardCell to should use
   */
  private static BoardCell createBoardCell(Coordinate coordinate) {
    if (shouldBeNullCell(coordinate)) {
      return BoardCell.NULL;
    } else if (shouldBePawnCell(coordinate)) {
      return BoardCell.PAWN;
    } else if (shouldBeWallCell(coordinate)) {
      return BoardCell.WALL;
    } else {
      throw new IllegalStateException("Couldn't get BoardCell for " + coordinate);
    }
  }

  /**
   * Checks if a BoardCell should be a Null BoardCell
   *
   * @param coordinate The Coordinate to check
   * @return True if the BoardCell should be Null, or false otherwise
   */
  private static boolean shouldBeNullCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 != 0
        && y % 2 != 0;
  }

  /**
   * Checks if a BoardCell should be a Pawn BoardCell
   *
   * @param coordinate The Coordinate to check
   * @return True if the BoardCell should be a Pawn BoardCell, or false otherwise
   */
  private static boolean shouldBePawnCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 == 0
        && y % 2 == 0;
  }

  /**
   * Checks if a BoardCell should be a wall BoardCell
   *
   * @param coordinate The Coordinate to check
   * @return True if the BoardCell should be a Wall BoardCell, or false otherwise
   */
  private static boolean shouldBeWallCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 != 0
        && y % 2 == 0
        || x % 2 == 0
        && y % 2 != 0;
  }
}
