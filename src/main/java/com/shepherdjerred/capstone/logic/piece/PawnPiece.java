package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.Player;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class PawnPiece implements Piece {

  private final Player owner;

  @Override
  public char toChar() {
    return (char) (owner.toInt() + '0');
  }
}
