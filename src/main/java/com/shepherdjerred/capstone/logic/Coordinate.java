package com.shepherdjerred.capstone.logic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Coordinate {

  @Getter
  private final int x;
  @Getter
  private final int y;

}
