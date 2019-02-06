package com.shepherdjerred.capstone.logic.board.cell;

import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class WallBoardCell implements BoardCell {

  private final Piece piece;

  @Override
  public BoardCell setPiece(Piece piece) {
    if (piece instanceof WallPiece || piece == NullPiece.INSTANCE) {
      return new WallBoardCell(piece);
    } else {
      throw new IllegalArgumentException(piece.toString());
    }
  }

  @Override
  public char toChar() {
    if (piece == NullPiece.INSTANCE) {
      return ' ';
    } else {
      return 'W';
    }
  }

  @Override
  public boolean hasPiece() {
    return piece != NullPiece.INSTANCE;
  }

  @Override
  public boolean hasPawn() {
    return false;
  }

  @Override
  public boolean hasWall() {
    return hasPiece();
  }
}
