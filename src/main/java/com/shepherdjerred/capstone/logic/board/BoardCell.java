package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BoardCell {

  private final Coordinate coordinate;
  private final CellType cellType;
  private final Piece piece;

  public BoardCell(BoardCell boardCell) {
    this.coordinate = boardCell.coordinate;
    this.cellType = boardCell.cellType;
    this.piece = boardCell.piece;
  }

  public BoardCell setPiece(Piece piece) {
    return new BoardCell(coordinate, cellType, piece);
  }

  public char toChar() {
    if (getPiece() instanceof NullPiece) {
      switch (getCellType()) {
        case PAWN:
          return '.';
        case WALL:
          return ' ';
        case INVALID:
          return ' ';
        default:
          throw new IllegalStateException("Unknown cell type " + getCellType());
      }
    } else {
      if (getPiece() instanceof WallPiece) {
        return 'W';
      } else if (getPiece() instanceof PawnPiece) {
        var pawn = (PawnPiece) getPiece();
        switch (pawn.getOwner()) {
          case ONE:
            return '1';
          case TWO:
            return '2';
          case THREE:
            return '3';
          case FOUR:
            return '4';
          default:
            throw new IllegalStateException("Unknown player " + pawn.getOwner());
        }
      } else {
        throw new IllegalStateException("Unknown piece " + getPiece());
      }
    }
  }

  public enum CellType {
    PAWN, WALL, INVALID
  }
}
