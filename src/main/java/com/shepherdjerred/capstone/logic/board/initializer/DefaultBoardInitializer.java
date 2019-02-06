package com.shepherdjerred.capstone.logic.board.initializer;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.BoardSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.cell.BoardCell;
import com.shepherdjerred.capstone.logic.board.cell.NullBoardCell;
import com.shepherdjerred.capstone.logic.board.cell.PawnBoardCell;
import com.shepherdjerred.capstone.logic.board.cell.WallBoardCell;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;

public enum DefaultBoardInitializer implements BoardInitializer {
  INSTANCE;

  /**
   * Creates a matrix of BoardCells
   *
   * @param boardSettings Settings to use when creating the board
   * @return A matrix of BoardCells based on the board settings
   */
  public BoardCell[][] createBoardCells(BoardSettings boardSettings) {
    var gridSize = boardSettings.getGridSize();
    var boardCells = new BoardCell[gridSize][gridSize];
    for (int x = 0; x < gridSize; x++) {
      for (int y = 0; y < gridSize; y++) {
        var boardCell = createBoardCell(boardSettings, new Coordinate(x, y));
        boardCells[x][y] = boardCell;
      }
    }

    return boardCells;
  }

  /**
   * Creates a single BoardCell
   *
   * @param settings Settings to use when creating the cell
   * @param coordinate The coordinate that the cell will exist
   * @return The BoardCell that should exist a the coordinate
   */
  private BoardCell createBoardCell(BoardSettings settings, Coordinate coordinate) {
    if (shouldBeNullCell(coordinate)) {
      return NullBoardCell.INSTANCE;
    } else if (shouldBePawnCell(coordinate)) {
      var piece = getPieceForCoordinate(settings, coordinate);
      return new PawnBoardCell(piece);
    } else if (shouldBeWallCell(coordinate)) {
      return new WallBoardCell(NullPiece.INSTANCE);
    }

    throw new IllegalStateException("Couldn't get cell type for " + coordinate);
  }

  /**
   * Gets the Piece for a BoardCell
   *
   * @param settings Settings to use when creating the cell
   * @param coordinate The coordinate that the cell will exist
   * @return The piece that should exist at the coordinate
   */
  private Piece getPieceForCoordinate(BoardSettings settings, Coordinate coordinate) {
    var gridSize = settings.getGridSize();
    var midpoint = gridSize / 2;
    var playerCount = settings.getPlayerCount();
    int x = coordinate.getX();
    int y = coordinate.getY();

    if (x == midpoint && y == 0) {
      return new PawnPiece(Player.ONE);
    }

    if (x == midpoint && y == gridSize - 1) {
      return new PawnPiece(Player.TWO);
    }

    if (playerCount == PlayerCount.FOUR) {
      if (x == 0 && y == midpoint) {
        return new PawnPiece(Player.THREE);
      }

      if (x == gridSize - 1 && y == midpoint) {
        return new PawnPiece(Player.FOUR);
      }
    }

    return NullPiece.INSTANCE;
  }

  /**
   * Checks if a cell should be null
   *
   * @param coordinate The coordinate to check
   * @return True if the cell should be null, or false otherwise
   */
  private boolean shouldBeNullCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x % 2 != 0 && y % 2 != 0;
  }

  /**
   * Checks if a cell should be a pawn cell
   *
   * @param coordinate The coordinate to check
   * @return True if the cell should be a pawn cell, or false otherwise
   */
  private boolean shouldBePawnCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();
    return x % 2 == 0
        && y % 2 == 0;
  }

  /**
   * Checks if a cell should be a wall cell
   *
   * @param coordinate The coordinate to check
   * @return True if the cell should be a wall cell, or false otherwise
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
