package com.shepherdjerred.capstone.logic.match;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

  @Test
  public void getInactivePlayers_ReturnsPlayerTwo_WhenPlayerOneIsPassedInTwoPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.ONE, PlayerCount.TWO);
    var actual = activePlayerTracker.getInactivePlayers();
    var expected = QuoridorPlayer.TWO;
    assertTrue(actual.contains(expected));
    assertEquals(1, actual.size());
  }

  @Test
  public void getInactivePlayers_ReturnsPlayerOne_WhenPlayerTwoIsPassedInTwoPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.TWO, PlayerCount.TWO);
    var actual = activePlayerTracker.getInactivePlayers();
    var expected = QuoridorPlayer.ONE;
    assertTrue(actual.contains(expected));
    assertEquals(1, actual.size());
  }

  @Test
  public void getInactivePlayers_ReturnsPlayersTwoThreeFour_WhenPlayerOneIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.ONE, PlayerCount.FOUR);
    var actual = activePlayerTracker.getInactivePlayers();
    var expectedOne = QuoridorPlayer.TWO;
    var expectedTwo = QuoridorPlayer.THREE;
    var expectedThree = QuoridorPlayer.FOUR;
    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertTrue(actual.contains(expectedThree));
    assertEquals(3, actual.size());
  }

  @Test
  public void getInactivePlayers_ReturnsPlayersOneThreeFour_WhenPlayerTwoIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.TWO, PlayerCount.FOUR);
    var actual = activePlayerTracker.getInactivePlayers();
    var expectedOne = QuoridorPlayer.ONE;
    var expectedTwo = QuoridorPlayer.THREE;
    var expectedThree = QuoridorPlayer.FOUR;
    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertTrue(actual.contains(expectedThree));
    assertEquals(3, actual.size());
  }

  @Test
  public void getInactivePlayers_ReturnsPlayersOneTwoFour_WhenPlayerThreeIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.THREE, PlayerCount.FOUR);
    var actual = activePlayerTracker.getInactivePlayers();
    var expectedOne = QuoridorPlayer.ONE;
    var expectedTwo = QuoridorPlayer.TWO;
    var expectedThree = QuoridorPlayer.FOUR;
    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertTrue(actual.contains(expectedThree));
    assertEquals(3, actual.size());
  }

  @Test
  public void getInactivePlayers_ReturnsPlayersOneTwoThree_WhenPlayerFourIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(QuoridorPlayer.FOUR, PlayerCount.FOUR);
    var actual = activePlayerTracker.getInactivePlayers();
    var expectedOne = QuoridorPlayer.ONE;
    var expectedTwo = QuoridorPlayer.TWO;
    var expectedThree = QuoridorPlayer.THREE;
    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertTrue(actual.contains(expectedThree));
    assertEquals(3, actual.size());
  }
}
