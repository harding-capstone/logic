package com.shepherdjerred.capstone.logic.board.validators.placewall;

import static org.mockito.Mockito.when;

import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallLocation;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.rules.placewall.WallPieceLocationCoordinatesAreFreeValidatorRule;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Log4j2
public class WallPieceLocationCoordinatesAreFreeValidatorRuleTest {

  @Mock
  private Match match;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void validate_returnsFalse_WhenACoordinateIsNotFree() {
    var validator = new WallPieceLocationCoordinatesAreFreeValidatorRule();
    var board = QuoridorBoard.from(new BoardSettings(9, PlayerCount.TWO));

    var wall = new WallLocation(new Coordinate(8, 1),
        new Coordinate(7, 1),
        new Coordinate(6, 1));
    var offsetWallOnLeft = new WallLocation(new Coordinate(6, 1),
        new Coordinate(5, 1),
        new Coordinate(4, 1));
    var offsetWallOnRight = new WallLocation(new Coordinate(8, 1),
        new Coordinate(9, 1),
        new Coordinate(10, 1));

    board = board.placeWall(QuoridorPlayer.ONE, wall);
    when(match.getBoard()).thenReturn(board);

    var turnOne = new PlaceWallTurn(QuoridorPlayer.ONE, wall);
    var turnTwo = new PlaceWallTurn(QuoridorPlayer.ONE, offsetWallOnLeft);
    var turnThree = new PlaceWallTurn(QuoridorPlayer.ONE, offsetWallOnRight);

    var resultOne = validator.validate(match, turnOne);
    var resultTwo = validator.validate(match, turnTwo);
    var resultThree = validator.validate(match, turnThree);

    Assert.assertTrue(resultOne.isError());
    Assert.assertTrue(resultOne.getErrors().contains(ErrorMessage.COORDINATES_NOT_EMPTY));
    Assert.assertTrue(resultTwo.isError());
    Assert.assertTrue(resultTwo.getErrors().contains(ErrorMessage.COORDINATES_NOT_EMPTY));
    Assert.assertTrue(resultThree.isError());
    Assert.assertTrue(resultThree.getErrors().contains(ErrorMessage.COORDINATES_NOT_EMPTY));
  }
}
