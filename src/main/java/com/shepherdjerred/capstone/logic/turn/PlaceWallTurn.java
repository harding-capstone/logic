package com.shepherdjerred.capstone.logic.turn;

import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.player.PlayerId;
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

  private final WallPieceLocation location;
}
