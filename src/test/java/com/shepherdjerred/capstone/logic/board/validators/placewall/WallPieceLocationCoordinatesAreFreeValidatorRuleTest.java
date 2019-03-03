package com.shepherdjerred.capstone.logic.board.validators.placewall;

import static org.mockito.Mockito.when;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validators.rules.placewall.WallPieceLocationCoordinatesAreFreeValidatorRule;
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
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));

    var wall = new WallPieceLocation(new Coordinate(8, 1),
        new Coordinate(7, 1),
        new Coordinate(6, 1));
    var offsetWallOnLeft = new WallPieceLocation(new Coordinate(6, 1),
        new Coordinate(5, 1),
        new Coordinate(4, 1));
    var offsetWallOnRight = new WallPieceLocation(new Coordinate(8, 1),
        new Coordinate(9, 1),
        new Coordinate(10, 1));

    board = board.placeWall(PlayerId.ONE, wall);
    when(match.getBoard()).thenReturn(board);

    var turnOne = new PlaceWallTurn(PlayerId.ONE, wall);
    var turnTwo = new PlaceWallTurn(PlayerId.ONE, offsetWallOnLeft);
    var turnThree = new PlaceWallTurn(PlayerId.ONE, offsetWallOnRight);

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
