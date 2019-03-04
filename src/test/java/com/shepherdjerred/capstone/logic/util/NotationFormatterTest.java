package com.shepherdjerred.capstone.logic.util;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import org.junit.Test;

public class NotationFormatterTest {

  @Test
  public void turnToString_returnsCorrectNotation_WhenMovingToBottomLeft() {
    var movePawnTurn = new NormalMovePawnTurn(PlayerId.ONE,
        new Coordinate(2, 0),
        new Coordinate(0, 0));

    var actual = NotationFormatter.turnToString(movePawnTurn);
    var expected = "a1";

    assertEquals(expected, actual);
  }

  @Test
  public void turnToString_returnsCorrectNotation_WhenMovingToBottomRight() {
    var movePawnTurn = new NormalMovePawnTurn(PlayerId.ONE,
        new Coordinate(14, 0),
        new Coordinate(16, 0));

    var actual = NotationFormatter.turnToString(movePawnTurn);
    var expected = "i1";

    assertEquals(expected, actual);
  }

  @Test
  public void turnToString_returnsCorrectNotation_WhenMovingToTopLeft() {
    var movePawnTurn = new NormalMovePawnTurn(PlayerId.ONE,
        new Coordinate(0, 14),
        new Coordinate(0, 16));

    var actual = NotationFormatter.turnToString(movePawnTurn);
    var expected = "a9";

    assertEquals(expected, actual);
  }

  @Test
  public void turnToString_returnsCorrectNotation_WhenMovingToTopRight() {
    var movePawnTurn = new NormalMovePawnTurn(PlayerId.ONE,
        new Coordinate(16, 14),
        new Coordinate(16, 16));

    var actual = NotationFormatter.turnToString(movePawnTurn);
    var expected = "i9";

    assertEquals(expected, actual);
  }

  @Test
  public void turnToString_returnsCorrectNotation_WhenPlacingWallHorizontally() {
    var placeWallTurn = new PlaceWallTurn(PlayerId.ONE,
        new WallPieceLocation(new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1)));

    var actual = NotationFormatter.turnToString(placeWallTurn);
    var expected = "a1h";

    assertEquals(expected, actual);
  }

  @Test
  public void turnToString_returnsCorrectNotation_WhenPlacingWallVertically() {
    var placeWallTurn = new PlaceWallTurn(PlayerId.ONE,
        new WallPieceLocation(new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2)));

    var actual = NotationFormatter.turnToString(placeWallTurn);
    var expected = "a1v";

    assertEquals(expected, actual);
  }

  @Test
  public void stringToTurn_returnsCorrectTurn_WhenMovingPawnToBottomLeft() {
    var notation = "a1";

    var actual = NotationFormatter.stringToTurn(notation);
    var expected = new NormalMovePawnTurn(PlayerId.NULL, null, new Coordinate(0, 0));

    assertEquals(expected, actual);
  }

  @Test
  public void stringToTurn_returnsCorrectTurn_WhenMovingPawnToBottomRight() {
    var notation = "i1";

    var actual = NotationFormatter.stringToTurn(notation);
    var expected = new NormalMovePawnTurn(PlayerId.NULL, null, new Coordinate(16, 0));

    assertEquals(expected, actual);
  }

  @Test
  public void stringToTurn_returnsCorrectTurn_WhenMovingPawnToTopLeft() {
    var notation = "a9";

    var actual = NotationFormatter.stringToTurn(notation);
    var expected = new NormalMovePawnTurn(PlayerId.NULL, null, new Coordinate(0, 16));

    assertEquals(expected, actual);
  }

  @Test
  public void stringToTurn_returnsCorrectTurn_WhenMovingPawnToTopRight() {
    var notation = "i9";

    var actual = NotationFormatter.stringToTurn(notation);
    var expected = new NormalMovePawnTurn(PlayerId.NULL, null, new Coordinate(16, 16));

    assertEquals(expected, actual);
  }

  @Test
  public void stringToTurn_returnsCorrectTurn_WhenPlacingWallHorizontally() {
    var notation = "a1h";

    var actual = NotationFormatter.stringToTurn(notation);
    var expected = new PlaceWallTurn(PlayerId.NULL,
        new WallPieceLocation(new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1)));

    assertEquals(expected, actual);
  }

  @Test
  public void stringToTurn_returnsCorrectTurn_WhenPlacingWallVertically() {
    var notation = "a1v";

    var actual = NotationFormatter.stringToTurn(notation);
    var expected = new PlaceWallTurn(PlayerId.NULL,
        new WallPieceLocation(new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2)));

    assertEquals(expected, actual);
  }
}
