package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import org.junit.Test;

public class FormatterTest {

  @Test
  public void oh() {
    var match = Match.from(new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    var formatter = new MatchFormatter();

    System.out.println(formatter.matchToString(match));
  }
}
