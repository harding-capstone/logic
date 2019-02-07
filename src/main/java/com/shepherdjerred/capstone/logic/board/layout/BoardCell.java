package com.shepherdjerred.capstone.logic.board.layout;

import lombok.ToString;

/**
 * This enum represents the three types of cells there are. A PAWN BoardCell can have a PawnPiece
 * placed on it, a WALL BoardCell can have a WallPiece placed on it, and a NullCell cannot have any
 * piece placed on it. It is used for the places in the BoardLayout array where four walls meet.
 */
@ToString
public enum BoardCell {
  PAWN, WALL, NULL;

  public char toChar() {
    if (this == PAWN) {
      return '■';
    } else if (this == WALL) {
      return ' ';
    } else if (this == NULL) {
      return ' ';
    } else {
      throw new IllegalStateException("Unknown BoardCell " + this);
    }
  }

}
