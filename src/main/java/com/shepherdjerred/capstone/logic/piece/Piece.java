package com.shepherdjerred.capstone.logic.piece;

import com.shepherdjerred.capstone.logic.player.Player;

public interface Piece {

  Player getOwner();

  char toChar();
}
