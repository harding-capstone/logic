package com.shepherdjerred.capstone.logic.turn;

import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MovePawnTurn implements Turn {

  private final Player causer;
  private final MoveType moveType;
  private final Coordinate source;
  private final Coordinate destination;

  public enum MoveType {
    NORMAL, JUMP_STRAIGHT, JUMP_DIAGONAL
  }
}
