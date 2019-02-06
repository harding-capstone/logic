package com.shepherdjerred.capstone.logic;

public enum Player {
  ONE, TWO, THREE, FOUR, NULL;

  public int toInt() {
    return this.ordinal() + 1;
  }
}
