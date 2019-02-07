package com.shepherdjerred.capstone.logic.player.exception;

import com.shepherdjerred.capstone.logic.player.Player;

public class InvalidPlayerException extends RuntimeException {
  public InvalidPlayerException(Player player) {
    super(player.toString());
  }
}
