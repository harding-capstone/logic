package com.shepherdjerred.capstone.logic.board.validators.placewall;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.board.search.AStarBoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.rules.placewall.WallDoesntBlockPawnsValidatorRule;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@Log4j2
public class WallDoesntBlockPawnsValidatorTest {

  @Mock
  private Match match;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testValidator_returnError_whenPawnIsBlocked() {
    var boardSearch = new AStarBoardSearch();
    var goals = new PlayerGoals();
    var validator = new WallDoesntBlockPawnsValidatorRule(boardSearch, goals);
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var leftWall = new WallPieceLocation(new Coordinate(7, 0),
        new Coordinate(7, 1),
        new Coordinate(7, 2));
    var rightWall = new WallPieceLocation(new Coordinate(9, 0),
        new Coordinate(9, 1),
        new Coordinate(9, 2));
    var topWall = new WallPieceLocation(new Coordinate(8, 3),
        new Coordinate(9, 3),
        new Coordinate(10, 3));
    board = board.placeWall(PlayerId.ONE, leftWall);
    board = board.placeWall(PlayerId.ONE, rightWall);
    var turn = new PlaceWallTurn(PlayerId.ONE, topWall);

    Mockito.when(match.getBoard()).thenReturn(board);

    var actual = validator.validate(match, turn);

    Assert.assertTrue(actual.isError());
    Assert.assertTrue(actual.getErrors().contains(ErrorMessage.WALL_BLOCKS_PAWN_PATH));
  }
}
