package com.shepherdjerred.capstone.logic.match;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import org.junit.Test;

public class ActivePlayerTrackerTest {
  @Test
  public void nextActivePlayer_ReturnsPlayerTwo_WhenPlayerOneIsPassedInTwoPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.ONE, PlayerCount.TWO);
    var actual = activePlayerTracker.getNextActivePlayerId();
    var expected = QuoridorPlayer.TWO;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerOne_WhenPlayerTwoIsPassedInTwoPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.TWO, PlayerCount.TWO);
    var actual = activePlayerTracker.getNextActivePlayerId();
    var expected = QuoridorPlayer.ONE;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerTwo_WhenPlayerOneIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.ONE, PlayerCount.FOUR);
    var actual = activePlayerTracker.getNextActivePlayerId();
    var expected = QuoridorPlayer.TWO;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerThree_WhenPlayerTwoIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.TWO, PlayerCount.FOUR);
    var actual = activePlayerTracker.getNextActivePlayerId();
    var expected = QuoridorPlayer.THREE;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerFour_WhenPlayerThreeIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.THREE, PlayerCount.FOUR);
    var actual = activePlayerTracker.getNextActivePlayerId();
    var expected = QuoridorPlayer.FOUR;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerOne_WhenPlayerFourIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.FOUR, PlayerCount.FOUR);
    var actual = activePlayerTracker.getNextActivePlayerId();
    var expected = QuoridorPlayer.ONE;
    assertEquals(actual, expected);
  }
}
