package com.shepherdjerred.capstone.logic.logic;

import com.shepherdjerred.capstone.logic.Match;
import com.shepherdjerred.capstone.logic.MatchSettings;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.BoardSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import org.junit.Test;

public class BoardTest {
  @Test
  public void doThing() {
    BoardSettings boardSettings = new BoardSettings(PlayerCount.TWO, 9);
    MatchSettings matchSettings = new MatchSettings(MatchSettings.PlayerCount.TWO, boardSettings);

    Match match = new Match(matchSettings, TurnValidatorFactory.INSTANCE, TurnEnactorFactory.INSTANCE);

    var firstTurn = new MovePawnTurn(Player.ONE, new Coordinate(9 , 1), new Coordinate(9, 3));
    var secondTurn = new PlaceWallTurn(Player.TWO, new Coordinate(10, 3), new Coordinate(10, 1));

    try {
      var match1 = match.doTurn(firstTurn);
      var match2 = match1.doTurn(secondTurn);

      System.out.println(match.getBoard().toFormattedString());
      System.out.println(match1.getBoard().toFormattedString());
      System.out.println(match2.getBoard().toFormattedString());

    } catch (InvalidTurnException e) {
      e.printStackTrace();
    }
  }
}
