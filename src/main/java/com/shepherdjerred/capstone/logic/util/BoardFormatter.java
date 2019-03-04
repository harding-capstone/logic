package com.shepherdjerred.capstone.logic.util;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.piece.NullPiece;

public class BoardFormatter {

  public String boardToString(Board board) {
    var gridSize = board.getBoardSettings().getGridSize();

    var sb = new StringBuilder();
    for (int y = gridSize - 1; y >= -3; y--) {
      if (y >= 0) {
        for (int x = 0; x < gridSize; x++) {
          var coordinate = new Coordinate(x, y);
          var displayChar = coordinateToChar(board, coordinate);
          if (x == 0) {
            sb.append(String.format("\n%02d ▍ ", y));
          }
          sb.append(displayChar);
        }
      } else {
        for (int x = 0; x < gridSize; x++) {
          if (x == 0) {
            sb.append("\n     ");
          }
          if (y == -1) {
            sb.append("▂");
          } else if (y == -2) {
            if (x / 10 < 1) {
              sb.append(x);
            } else {
              sb.append(x / 10);
            }
          } else {
            if (x / 10 >= 1) {
              sb.append(x % 10);
            } else {
              sb.append(" ");
            }
          }
        }

      }
    }
    sb.append("\n");
    return sb.toString();
  }

  public char coordinateToChar(Board board, Coordinate coordinate) {
    var cell = board.getBoardCell(coordinate).toChar();
    var piece = board.getPiece(coordinate);
    return piece == NullPiece.INSTANCE ? cell : piece.toChar();
  }
}
