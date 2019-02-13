package com.shepherdjerred.capstone.logic.turn;

import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class PlaceWallTurn implements Turn {

  private final PlayerId causer;
  private final Coordinate firstCoordinate;
  private final Coordinate secondCoordinate;
}
