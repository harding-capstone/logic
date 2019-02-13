package com.shepherdjerred.capstone.logic.player.exception;

import com.shepherdjerred.capstone.logic.player.PlayerId;

public class InvalidPlayerException extends RuntimeException {
  public InvalidPlayerException(PlayerId playerId) {
    super(playerId.toString());
  }
}
