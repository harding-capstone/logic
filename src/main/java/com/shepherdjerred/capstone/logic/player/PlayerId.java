package com.shepherdjerred.capstone.logic.player;

public enum PlayerId {
  ONE, TWO, THREE, FOUR, NULL;

  public int toInt() {
    return this.ordinal() + 1;
  }

  public static PlayerId fromInt(int i) {
    return PlayerId.values()[i - 1];
  }
}
