package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class WallPiece implements Piece {

  private final Player owner;

  @Override
  public char toChar() {
    return 'W';
  }
}
