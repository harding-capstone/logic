package com.shepherdjerred.capstone.logic.board.cell;

import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class PawnCell implements BoardCell {

  private final Piece piece;

  @Override
  public BoardCell setPiece(Piece piece) {
    if (piece instanceof PawnPiece || piece == NullPiece.INSTANCE) {
      return new PawnCell(piece);
    } else {
      throw new IllegalArgumentException(piece.toString());
    }
  }

  @Override
  public char toChar() {
    if (piece == NullPiece.INSTANCE) {
      return '.';
    } else {
      return (char) piece.getOwner().toInt();
    }
  }

  @Override
  public boolean hasPiece() {
    return piece != NullPiece.INSTANCE;
  }

  @Override
  public boolean hasPawn() {
    return hasPiece();
  }

  @Override
  public boolean hasWall() {
    return false;
  }
}
