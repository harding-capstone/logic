package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import java.util.Set;

public class PlayerGoals {

  public Set<Coordinate> getGoalCoordinatesForPlayer(PlayerId playerId, int gridSize) {
    Set<Coordinate> goals = new HashSet<>();
    if (playerId == PlayerId.ONE) {
      for (int x = 0; x < gridSize - 1; x += 2) {
        goals.add(new Coordinate(x, gridSize - 1));
      }
    } else if (playerId == PlayerId.TWO) {
      for (int x = 0; x < gridSize - 1; x += 2) {
        goals.add(new Coordinate(x, 0));
      }
    } else if (playerId == PlayerId.THREE) {
      for (int y = 0; y < gridSize - 1; y += 2) {
        goals.add(new Coordinate(gridSize - 1, y));
      }
    } else if (playerId == PlayerId.FOUR) {
      for (int y = 0; y < gridSize - 1; y += 2) {
        goals.add(new Coordinate(0, y));
      }
    } else {
      throw new UnsupportedOperationException();
    }
    return goals;
  }
}
