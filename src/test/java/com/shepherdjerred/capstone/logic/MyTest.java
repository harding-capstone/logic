package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.BoardSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.initializer.DefaultMatchInitializer;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import org.junit.Test;

public class MyTest {

  @Test
  public void testMatch() throws InvalidTurnException {
    var boardSettings = new BoardSettings(PlayerCount.TWO, 9);
    var matchSettings = new MatchSettings(
        MatchSettings.PlayerCount.TWO,
        10,
        boardSettings,
        Player.ONE
    );

    var match = new Match(
        matchSettings,
        TurnValidatorFactory.INSTANCE,
        TurnEnactorFactory.INSTANCE,
        DefaultMatchInitializer.INSTANCE);

    var firstTurn = new MovePawnTurn(Player.ONE, new Coordinate(9, 1), new Coordinate(9, 3));
    var secondTurn = new MovePawnTurn(Player.TWO, new Coordinate(9, 17), new Coordinate(9, 15));
    var thirdTurn = new PlaceWallTurn(Player.ONE, new Coordinate(9, 14), new Coordinate(11, 14));

    var matchAfterFirst = match.doTurn(firstTurn);
    var matchAfterSecond = matchAfterFirst.doTurn(secondTurn);
    var matchAfterThird = matchAfterSecond.doTurn(thirdTurn);

    System.out.println(match);
    System.out.println(match.getBoard().toFormattedString());

    System.out.println(matchAfterFirst);
    System.out.println(matchAfterFirst.getBoard().toFormattedString());

    System.out.println(matchAfterSecond);
    System.out.println(matchAfterSecond.getBoard().toFormattedString());

    System.out.println(matchAfterThird);
    System.out.println(matchAfterThird.getBoard().toFormattedString());
  }
}
