package com.shepherdjerred.capstone.logic.turn;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import org.junit.Ignore;
import org.junit.Test;

public class TurnGeneratorTest {

  @Ignore
  @Test
  public void generateValidTurns_returns60_onInitialMatchStateWithStandardBoard() {
    var match = Match.from(new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));
    var generator = new TurnGenerator();

    assertEquals(60, generator.generateValidTurns(match).size());
  }

  @Test
  public void generateInvalidTurns_returns1_onInitialMatchStateWithStandardBoard() {
    var match = Match.from(new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));
    var generator = new TurnGenerator();

    assertEquals(1, generator.generateInvalidTurns(match).size());
  }
}
