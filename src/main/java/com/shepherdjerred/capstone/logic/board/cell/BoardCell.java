package com.shepherdjerred.capstone.logic.board.cell;

import com.shepherdjerred.capstone.logic.piece.Piece;

public interface BoardCell {

  Piece getPiece();

  BoardCell setPiece(Piece piece);

  char toChar();

  boolean hasPiece();

  boolean hasPawn();

  boolean hasWall();

}
