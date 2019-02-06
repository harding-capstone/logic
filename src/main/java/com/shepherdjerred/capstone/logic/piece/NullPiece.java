package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.Player;

public enum NullPiece implements Piece {
  INSTANCE;

  @Override
  public Player getOwner() {
    return Player.NULL;
  }

  @Override
  public char toChar() {
    return ' ';
  }
}
