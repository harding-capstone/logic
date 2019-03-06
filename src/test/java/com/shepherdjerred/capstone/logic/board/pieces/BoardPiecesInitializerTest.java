package com.shepherdjerred.capstone.logic.board.pieces;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import org.junit.Assert;
import org.junit.Test;

public class BoardPiecesInitializerTest {

  @Test
  public void getInitialPawnPositions_createsMapOfSizeTwo_whenCreatingPiecesForTwoPlayers() {
    var settings = new BoardSettings(9, PlayerCount.TWO);
    var initializer = new BoardPiecesInitializer();

    var actual = initializer.getInitialPawnLocations(settings);

    Assert.assertEquals(2, actual.size());
  }
}
