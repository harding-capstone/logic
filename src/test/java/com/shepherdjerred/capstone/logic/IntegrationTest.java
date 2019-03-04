package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class IntegrationTest {

  @Test
  public void testMatch() {
    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, PlayerCount.TWO);
    var match = Match.from(matchSettings, boardSettings);

    System.out.print(new MatchFormatter().matchToString(match));

    var turnGenerator = new TurnGenerator(new TurnValidatorFactory());
//    var valid = turnGenerator.generateValidTurns(match);
//    var invalid = turnGenerator.generateInvalidTurns(match);

//    log.info("Invalid turns for player one at initial match state: " + invalid);
  }
}
