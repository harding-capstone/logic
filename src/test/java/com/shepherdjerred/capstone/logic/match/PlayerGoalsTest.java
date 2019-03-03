package com.shepherdjerred.capstone.logic.match;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class PlayerGoalsTest {

  @Test
  public void getGoalsCoordinatesForPlayer_returnsGoalCoordinatesForPlayerOne_whenCalledWithPlayerOneAndGridSizeOf17() {
    var playerGoals = new PlayerGoals();
    var actual = playerGoals.getGoalCoordinatesForPlayer(PlayerId.ONE, 17);
    Set<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(0, 16));
    expected.add(new Coordinate(2, 16));
    expected.add(new Coordinate(4, 16));
    expected.add(new Coordinate(6, 16));
    expected.add(new Coordinate(8, 16));
    expected.add(new Coordinate(10, 16));
    expected.add(new Coordinate(12, 16));
    expected.add(new Coordinate(14, 16));
    expected.add(new Coordinate(16, 16));

    assertEquals(expected, actual);
  }

  @Test
  public void getGoalsCoordinatesForPlayer_returnsGoalCoordinatesForPlayerTwo_whenCalledWithPlayerTwoAndGridSizeOf17() {
    var playerGoals = new PlayerGoals();
    var actual = playerGoals.getGoalCoordinatesForPlayer(PlayerId.TWO, 17);
    Set<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(0, 0));
    expected.add(new Coordinate(2, 0));
    expected.add(new Coordinate(4, 0));
    expected.add(new Coordinate(6, 0));
    expected.add(new Coordinate(8, 0));
    expected.add(new Coordinate(10, 0));
    expected.add(new Coordinate(12, 0));
    expected.add(new Coordinate(14, 0));
    expected.add(new Coordinate(16, 0));

    assertEquals(expected, actual);
  }

  @Test
  public void getGoalsCoordinatesForPlayer_returnsGoalCoordinatesForPlayerThree_whenCalledWithPlayerThreeAndGridSizeOf17() {
    var playerGoals = new PlayerGoals();
    var actual = playerGoals.getGoalCoordinatesForPlayer(PlayerId.THREE, 17);
    Set<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(16, 0));
    expected.add(new Coordinate(16, 2));
    expected.add(new Coordinate(16, 4));
    expected.add(new Coordinate(16, 6));
    expected.add(new Coordinate(16, 8));
    expected.add(new Coordinate(16, 10));
    expected.add(new Coordinate(16, 12));
    expected.add(new Coordinate(16, 14));
    expected.add(new Coordinate(16, 16));

    assertEquals(expected, actual);
  }

  @Test
  public void getGoalsCoordinatesForPlayer_returnsGoalCoordinatesForPlayerFour_whenCalledWithPlayerForAndGridSizeOf17() {
    var playerGoals = new PlayerGoals();
    var actual = playerGoals.getGoalCoordinatesForPlayer(PlayerId.FOUR, 17);
    Set<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(0, 0));
    expected.add(new Coordinate(0, 2));
    expected.add(new Coordinate(0, 4));
    expected.add(new Coordinate(0, 6));
    expected.add(new Coordinate(0, 8));
    expected.add(new Coordinate(0, 10));
    expected.add(new Coordinate(0, 12));
    expected.add(new Coordinate(0, 14));
    expected.add(new Coordinate(0, 16));

    assertEquals(expected, actual);
  }
}
