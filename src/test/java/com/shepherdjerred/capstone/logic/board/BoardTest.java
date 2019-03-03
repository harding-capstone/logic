package com.shepherdjerred.capstone.logic.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import org.junit.Test;

public class BoardTest {

  @Test
  public void getAdjacentPawnSpaces_returnsTwoCoordinates_whenFindingSpacesAtBottomLeft() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var coord = new Coordinate(0, 0);

    var actual = board.getAdjacentPawnSpaces(coord);
    var expectedOne = new Coordinate(2, 0);
    var expectedTwo = new Coordinate(0, 2);

    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertEquals(2, actual.size());
  }

  @Test
  public void getAdjacentPawnSpaces_returnsTwoCoordinates_whenFindingSpacesAtBottomRight() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var coord = new Coordinate(16, 0);

    var actual = board.getAdjacentPawnSpaces(coord);
    var expectedOne = new Coordinate(16, 2);
    var expectedTwo = new Coordinate(14, 0);

    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertEquals(2, actual.size());
  }

  @Test
  public void getAdjacentPawnSpaces_returnsTwoCoordinates_whenFindingSpacesAtTopLeft() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var coord = new Coordinate(0, 16);

    var actual = board.getAdjacentPawnSpaces(coord);
    var expectedOne = new Coordinate(2, 16);
    var expectedTwo = new Coordinate(0, 14);

    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertEquals(2, actual.size());
  }

  @Test
  public void getAdjacentPawnSpaces_returnsTwoCoordinates_whenFindingSpacesAtTopRight() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var coord = new Coordinate(16, 16);

    var actual = board.getAdjacentPawnSpaces(coord);
    var expectedOne = new Coordinate(14, 16);
    var expectedTwo = new Coordinate(16, 14);

    assertTrue(actual.contains(expectedOne));
    assertTrue(actual.contains(expectedTwo));
    assertEquals(2, actual.size());
  }

  @Test
  public void getAdjacentPawnSpaces_returnsTopRightSpace_whenFindingSpacesDirectlyBelowTopRight() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var coord = new Coordinate(16, 14);

    var actual = board.getAdjacentPawnSpaces(coord);
    var expected = new Coordinate(16, 16);

    assertTrue(actual.contains(expected));
  }


  @Test
  public void getAdjacentPawnSpaces_returnsBottomLeftSpace_whenFindingSpacesDirectlyAboveBottomLeft() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var coord = new Coordinate(0, 2);

    var actual = board.getAdjacentPawnSpaces(coord);
    var expected = new Coordinate(0, 0);

    assertTrue(actual.contains(expected));
  }

  @Test
  public void hasWall_returnsTrue_whenBoardHasWallOnWallVertexCell() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var wallLocation = new WallPieceLocation(new Coordinate(0, 1),
        new Coordinate(1, 1),
        new Coordinate(2, 1));
    board = board.placeWall(PlayerId.ONE, wallLocation);
    assertTrue(board.hasWall(new Coordinate(1, 1)));
  }

  @Test
  public void hasWall_returnsTrue_whenBoardHasWallOnWallCell() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var wallLocation = new WallPieceLocation(new Coordinate(0, 1),
        new Coordinate(1, 1),
        new Coordinate(2, 1));
    board = board.placeWall(PlayerId.ONE, wallLocation);
    assertTrue(board.hasWall(new Coordinate(0, 1)));
    assertTrue(board.hasWall(new Coordinate(2, 1)));
  }

  @Test
  public void hasWall_returnFalse_whenBoardDoesntHaveWallOnWallVertexCell() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    assertFalse(board.hasWall(new Coordinate(1, 1)));
  }

  @Test
  public void hasWall_returnsFalse_whenBoardDoesntHaveWallOnWallCell() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    assertFalse(board.hasWall(new Coordinate(0, 1)));
    assertFalse(board.hasWall(new Coordinate(0, 2)));
  }

  @Test
  public void hasWall_returnsFalse_whenCalledWithPawnCell() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    assertFalse(board.hasWall(new Coordinate(0, 0)));
  }
}
