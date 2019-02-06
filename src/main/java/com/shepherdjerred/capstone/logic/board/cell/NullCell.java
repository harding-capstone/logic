package com.shepherdjerred.capstone.logic.board.cell;

import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import lombok.ToString;

@ToString
public enum NullCell implements BoardCell {
  INSTANCE;

  @Override
  public Piece getPiece() {
    return NullPiece.INSTANCE;
  }

  @Override
  public BoardCell setPiece(Piece piece) {
    throw new IllegalArgumentException("Cannot set piece of a NullCell " + piece.toString());
  }

  @Override
  public char toChar() {
    return ' ';
  }

  @Override
  public boolean hasPiece() {
    return false;
  }

  @Override
  public boolean hasPawn() {
    return false;
  }

  @Override
  public boolean hasWall() {
    return false;
  }
}
