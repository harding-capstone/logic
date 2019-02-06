package com.shepherdjerred.capstone.logic.board.layout;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.layout.initializer.BoardLayoutCreator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// TODO We could probably find a better name than 'BoardLayout'
@ToString
@EqualsAndHashCode
public final class BoardLayout {

  private final BoardSettings boardSettings;
  private final BoardCell[][] boardCells;

  /**
   * Constructor for a new boardState
   *
   * @param boardSettings Settings for the boardState
   * @param boardLayoutCreator An initializer to create the BoardLayout
   */
  public BoardLayout(BoardSettings boardSettings, BoardLayoutCreator boardLayoutCreator) {
    this.boardSettings = boardSettings;
    this.boardCells = boardLayoutCreator.createBoardCells(boardSettings);
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

  public boolean canHoldWall(Coordinate coordinate) {
    return getCell(coordinate) == BoardCell.WALL;
  }

  public boolean canHoldPawn(Coordinate coordinate) {
    return getCell(coordinate) == BoardCell.PAWN;
  }

  public boolean isCoordinateInvalid(Coordinate coordinate) {
    return !isCoordinateValid(coordinate);
  }

  public boolean isCoordinateValid(Coordinate coordinate) {
    var gridSize = boardSettings.getGridSize();
    var x = coordinate.getX();
    var y = coordinate.getY();
    return x >= 0
        && x < gridSize - 1
        && y >= 0
        && y < gridSize - 1;
  }

  /**
   * @return A formatted string representing the boardState state
   */
  public String toFormattedString() {
    var sb = new StringBuilder();
    for (int y = boardSettings.getGridSize() - 1; y >= 0; y--) {
      for (int x = 0; x < boardSettings.getGridSize(); x++) {
        var coordinate = new Coordinate(x, y);
        var cell = getCell(coordinate).toChar();
        if (x == 0) {
          sb.append('\n');
        }
        sb.append(cell);
      }
    }
    return sb.toString();
  }
}
