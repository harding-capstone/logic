package com.shepherdjerred.capstone.logic.util;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import java.util.List;

/**
 * Utility class for formatting a match state as a string
 */
public final class MatchFormatter {

  public String matchesToString(List<Match> states) {
    var sb = new StringBuilder();
    states.forEach(state -> sb.append(matchToString(state)).append("\n\n"));
    return sb.toString();
  }

  public String matchToString(Match match) {
    var gridSize = match.getBoard().getBoardSettings().getGridSize();

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
