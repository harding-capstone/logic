package com.shepherdjerred.capstone.logic.turn;

import com.shepherdjerred.capstone.logic.Cell;
import com.shepherdjerred.capstone.logic.Player;
import java.util.Set;
import lombok.Data;

@Data
public class PlaceWallTurn implements Turn {
  private Player invoker;
  private Set<Cell> cells;
}
