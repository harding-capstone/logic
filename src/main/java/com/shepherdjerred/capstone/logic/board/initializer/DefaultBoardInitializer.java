package com.shepherdjerred.capstone.logic.board.initializer;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardCell;
import com.shepherdjerred.capstone.logic.board.BoardCell.CellType;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.BoardSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;

public enum DefaultBoardInitializer implements BoardInitializer {
  INSTANCE;

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

  private BoardCell createBoardCell(BoardSettings settings, Coordinate coordinate) {
    var cellType = getCellTypeForCoordinate(coordinate);
    var piece = getPieceForCoordinate(settings, coordinate);
    return new BoardCell(cellType, piece);
  }

  private Piece getPieceForCoordinate(BoardSettings settings, Coordinate coordinate) {
    var playerCount = settings.getPlayerCount();
    int x = coordinate.getX();
    int y = coordinate.getY();

    if (x == 9 && y == 1) {
      return new PawnPiece(Player.ONE);
    }

    if (x == 9 && y == 17) {
      return new PawnPiece(Player.TWO);
    }

    if (playerCount == PlayerCount.FOUR) {
      if (x == 1 && y == 9) {
        return new PawnPiece(Player.THREE);
      }

      if (x == 17 && y == 9) {
        return new PawnPiece(Player.FOUR);
      }
    }

    return NullPiece.INSTANCE;
  }

  private CellType getCellTypeForCoordinate(Coordinate coordinate) {

    if (isInvalidCell(coordinate)) {
      return CellType.NULL;
    } else if (isPawnCell(coordinate)) {
      return CellType.PAWN;
    } else if (isWallCell(coordinate)) {
      return CellType.WALL;
    }

    throw new IllegalStateException("Couldn't get cell type for " + coordinate);
  }

  private boolean isInvalidCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    return x == 0 && y == 0
        || x == 0 && y % 2 == 0
        || y == 0 && x % 2 == 0
        || x % 2 == 0 && y % 2 == 0;
  }

  private boolean isPawnCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();
    return x % 2 != 0 && y % 2 != 0;
  }

  private boolean isWallCell(Coordinate coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();
    return x % 2 != 0 && y % 2 == 0 || x % 2 == 0 && y % 2 != 0;
  }
}
