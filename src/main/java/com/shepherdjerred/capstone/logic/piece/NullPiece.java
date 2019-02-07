package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.player.Player;
import lombok.ToString;

/**
 * Represents an empty piece
 */
@ToString
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
