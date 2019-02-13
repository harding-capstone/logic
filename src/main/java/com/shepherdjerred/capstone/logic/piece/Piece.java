package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.player.PlayerId;

public interface Piece {

  PlayerId getOwner();

  char toChar();
}
