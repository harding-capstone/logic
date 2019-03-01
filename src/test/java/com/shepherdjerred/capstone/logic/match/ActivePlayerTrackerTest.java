package com.shepherdjerred.capstone.logic.match;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import org.junit.Test;

public class ActivePlayerTrackerTest {
  @Test
  public void nextActivePlayer_ReturnsPlayerTwo_WhenPlayerOneIsPassedInTwoPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(PlayerCount.TWO);
    var actual = activePlayerTracker.nextActivePlayer(PlayerId.ONE);
    var expected = PlayerId.TWO;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerOne_WhenPlayerTwoIsPassedInTwoPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(PlayerCount.TWO);
    var actual = activePlayerTracker.nextActivePlayer(PlayerId.TWO);
    var expected = PlayerId.ONE;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerTwo_WhenPlayerOneIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(PlayerCount.FOUR);
    var actual = activePlayerTracker.nextActivePlayer(PlayerId.ONE);
    var expected = PlayerId.TWO;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerThree_WhenPlayerTwoIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(PlayerCount.FOUR);
    var actual = activePlayerTracker.nextActivePlayer(PlayerId.TWO);
    var expected = PlayerId.THREE;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerFour_WhenPlayerThreeIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(PlayerCount.FOUR);
    var actual = activePlayerTracker.nextActivePlayer(PlayerId.THREE);
    var expected = PlayerId.FOUR;
    assertEquals(actual, expected);
  }

  @Test
  public void nextActivePlayer_ReturnsPlayerOne_WhenPlayerFourIsPassedInFourPlayerGame() {
    var activePlayerTracker = new ActivePlayerTracker(PlayerCount.FOUR);
    var actual = activePlayerTracker.nextActivePlayer(PlayerId.FOUR);
    var expected = PlayerId.ONE;
    assertEquals(actual, expected);
  }
}
