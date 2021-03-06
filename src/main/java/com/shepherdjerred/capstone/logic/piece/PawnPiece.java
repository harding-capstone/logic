package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PawnPiece implements Piece {

  private final QuoridorPlayer owner;

  @Override
  public char toChar() {
    return (char) (owner.toInt() + '0');
  }
}
