package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.ToString;

/**
 * Represents an empty piece
 */
@ToString
public enum NullPiece implements Piece {
  INSTANCE;

  @Override
  public PlayerId getOwner() {
    return PlayerId.NULL;
  }

  @Override
  public char toChar() {
    return ' ';
  }
}
