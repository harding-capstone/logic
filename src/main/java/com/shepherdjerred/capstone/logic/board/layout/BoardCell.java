package com.shepherdjerred.capstone.logic.board.layout;

import lombok.ToString;

@ToString
public enum BoardCell {
  PAWN, WALL, NULL;

  public char toChar() {
    if (this == PAWN) {
      return '·';
    } else if (this == WALL) {
      return ' ';
    } else if (this == NULL) {
      return ' ';
    } else {
      throw new IllegalStateException("Unknown boardState layout " + this);
    }
  }

}
