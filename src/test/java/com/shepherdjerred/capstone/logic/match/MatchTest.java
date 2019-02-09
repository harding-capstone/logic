package com.shepherdjerred.capstone.logic.match;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MatchTest {

  private TurnValidationResult failingResult;
  private TurnValidationResult passingResult;
  private Turn turn;
  private Match match;
  private MatchSettings matchSettings;
  private BoardSettings boardSettings;
  @Mock
  private Board board;
  @Mock
  private TurnEnactorFactory turnEnactorFactory;
  @Mock
  private TurnValidator turnValidator;
  @Mock
  private TurnEnactor turnEnactor;

  @Before
  public void setup() {
    setupObjects();
    setupMockBehavior();
  }

  // Be careful when changing, tests rely on these values
  private void setupObjects() {
    boardSettings = new BoardSettings(9);
    matchSettings = new MatchSettings(10, PlayerCount.TWO, Player.ONE);
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
    turn = new MovePawnTurn(Player.ONE,
        MoveType.NORMAL,
        new Coordinate(8, 0),
        new Coordinate(9, 2));
    failingResult = new TurnValidationResult(true);
    passingResult = new TurnValidationResult(false);
  }

  // Be careful when changing, tests rely on these values
  private void setupMockBehavior() {
    when(board.getBoardSettings()).thenReturn(boardSettings);
    when(turnValidator.isTurnValid(turn, match)).thenReturn(passingResult);
    when(turnEnactorFactory.getEnactor(turn)).thenReturn(turnEnactor);
    when(turnEnactor.enactTurn(turn, board)).thenReturn(board);
  }

  @Test
  public void getNextActivePlayer_ReturnsPlayerTwoWhenPlayerOneIsActiveInATwoPlayerGame() {
    assertEquals(Player.TWO, match.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayer_ReturnsPlayerOneWhenPlayerTwoIsTheActiveInATwoPlayerGame() {
    matchSettings = new MatchSettings(10, PlayerCount.TWO, Player.TWO);
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
    assertEquals(Player.ONE, match.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayer_ReturnsPlayerTwoWhenPlayerOneIsTheActiveInAFourPlayerGame() {
    matchSettings = new MatchSettings(10, PlayerCount.FOUR, Player.ONE);
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
    assertEquals(Player.TWO, match.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayer_ReturnsPlayerThreeWhenPlayerTwoIsTheActiveInAFourPlayerGame() {
    matchSettings = new MatchSettings(10, PlayerCount.FOUR, Player.TWO);
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
    assertEquals(Player.THREE, match.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayer_ReturnsPlayerFourWhenPlayerThreeIsTheActiveInAFourPlayerGame() {
    matchSettings = new MatchSettings(10, PlayerCount.FOUR, Player.THREE);
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
    assertEquals(Player.FOUR, match.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayer_ReturnsPlayerOneWhenPlayerFourIsTheActiveInAFourPlayerGame() {
    matchSettings = new MatchSettings(10, PlayerCount.FOUR, Player.FOUR);
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
    assertEquals(Player.ONE, match.getNextActivePlayer());
  }

  @Test
  public void getWalls_ReturnsTheNumberOfWallsAPlayerHas() {
    assertEquals(10, match.getWallsLeft(Player.ONE));
  }

  @Test
  public void startNewMatch_ReturnsMatch_WhenInputIsValid() {
    match = Match.startNewMatch(matchSettings, board, turnEnactorFactory, turnValidator);
  }

  @Test
  public void doTurn_ReturnsMatch_WhenInputIsValid() {
    var newMatch = match.doTurn(turn);
    assertNotNull(newMatch);
  }

  @Test
  public void doTurn_CallsTurnValidator() {
    match.doTurn(turn);
    verify(turnValidator, times(1)).isTurnValid(turn, match);
  }

  @Test(expected = InvalidTurnException.class)
  public void doTurn_ThrowsException_WhenTurnValidationFails() {
    when(turnValidator.isTurnValid(turn, match)).thenReturn(failingResult);
    match.doTurn(turn);
  }

  @Test
  public void doTurn_CallsEnactor_WhenTurnValidationSucceeds() {
    match.doTurn(turn);
    verify(turnEnactor, times(1)).enactTurn(turn, match.getBoard());
  }
}
