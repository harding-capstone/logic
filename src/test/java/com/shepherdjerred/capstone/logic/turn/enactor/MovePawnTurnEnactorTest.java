package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.initializer.MatchInitializer;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MovePawnTurnEnactorTest {

  private Match match;
  private MovePawnTurnEnactor turnEnactor = MovePawnTurnEnactor.INSTANCE;

  @Mock
  private TurnEnactorFactory defaultTurnEnactorFactory;
  @Mock
  private TurnValidatorFactory defaultTurnValidatorFactory;
  @Mock
  private MatchInitializer matchInitializer;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    var boardSettings = new BoardSettings(9);
    var matchSettings = new MatchSettings(
        MatchSettings.PlayerCount.TWO,
        10,
        boardSettings,
        Player.ONE
    );
    match = new Match(
        matchSettings,
        defaultTurnValidatorFactory,
        defaultTurnEnactorFactory,
        matchInitializer
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void enactTurn_ThrowsException_WhenNotGivenMovePawnTurn() {
    var turn = Mockito.mock(Turn.class);

    turnEnactor.enactTurn(turn, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void enactTurn_ThrowsException_WhenDestinationIsNotPawnCell() {
    var turn = new MovePawnTurn(Player.ONE, new Coordinate(8, 0), new Coordinate(7, 0));

    turnEnactor.enactTurn(turn, match);
  }

  @Test(expected = CoordinateOutOfBoundsException.class)
  public void enactTurn_ThrowsException_WhenDestinationIsOutOfBounds() {
    var turn = new MovePawnTurn(Player.ONE, new Coordinate(8, 0), new Coordinate(8, -1));

    turnEnactor.enactTurn(turn, match);
  }

  @Test
  public void enactTurn_ThrowsException_WhenDestinationIsTooFarAway() {
    var turn = new MovePawnTurn(Player.ONE, new Coordinate(8, 0), new Coordinate(8, 4));

    turnEnactor.enactTurn(turn, match);
  }
}
