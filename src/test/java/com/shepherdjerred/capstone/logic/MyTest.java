package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.MatchFormatter;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.initializer.DefaultMatchInitializer;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.enactor.DefaultTurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.validator.DefaultTurnValidatorFactory;
import org.junit.Test;

public class MyTest {

  @Test
  public void myTest() {
    var boardSettings = new BoardSettings(9);
    var matchSettings = new MatchSettings(PlayerCount.TWO, 10, boardSettings, Player.ONE);

    var matchState = new Match(
        matchSettings,
        DefaultTurnValidatorFactory.INSTANCE,
        DefaultTurnEnactorFactory.INSTANCE,
        DefaultMatchInitializer.INSTANCE
    );

    var turn1 = new MovePawnTurn(Player.ONE, new Coordinate(8, 0), new Coordinate(8, 2));
    var turn2 = new MovePawnTurn(Player.TWO, new Coordinate(8, 16), new Coordinate(8, 14));
    var turn3 = new PlaceWallTurn(Player.ONE, new Coordinate(8, 13), new Coordinate(6, 13));

    var match1 = matchState.doTurn(turn1);
    var match2 = match1.doTurn(turn2);
    var match3 = match2.doTurn(turn3);

    var matchFormatter = MatchFormatter.INSTANCE;

    System.out.println(matchFormatter.matchToString(matchState));
    System.out.println(matchFormatter.matchToString(match1));
    System.out.println(matchFormatter.matchToString(match2));
    System.out.println(matchFormatter.matchToString(match3));
  }
}