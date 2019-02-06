package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.NullPiece;

public enum MatchFormatter {
  INSTANCE;

  public String matchToString(Match match) {
    var gridSize = match.getMatchSettings().getBoardSettings().getGridSize();

    var sb = new StringBuilder();
    for (int y = gridSize - 1; y >= 0; y--) {
      for (int x = 0; x < gridSize; x++) {
        var coordinate = new Coordinate(x, y);
        var displayChar = coordinateToChar(match.getBoard(), coordinate);
        if (x == 0) {
          sb.append('\n');
        }
        sb.append(displayChar);
      }
    }
    return sb.toString();
  }

  public char coordinateToChar(Board board, Coordinate coordinate) {
    var cell = board.getBoardCell(coordinate).toChar();
    var piece = board.getPiece(coordinate);
    return piece == NullPiece.INSTANCE ? cell : piece.toChar();
  }
}
