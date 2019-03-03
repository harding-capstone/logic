package com.shepherdjerred.capstone.logic.board.search;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class BoardAStarSearchNodeTest {

  @Test
  public void getChildNodes_ReturnsTwoNodes_WhenAtBottomLeft() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var node = new BoardAStarSearchNode(0, board, new Coordinate(0, 0), new HashSet<>(), null);

    assertEquals(2, node.getChildNodes().size());
  }

  @Test
  public void getChildNodes_ReturnsTwoNodes_WhenAtBottomRight() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var node = new BoardAStarSearchNode(0, board, new Coordinate(16, 0), new HashSet<>(), null);

    assertEquals(2, node.getChildNodes().size());
  }

  @Test
  public void getChildNodes_ReturnsTwoNodes_WhenAtTopLeft() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var node = new BoardAStarSearchNode(0, board, new Coordinate(0, 16), new HashSet<>(), null);

    assertEquals(2, node.getChildNodes().size());
  }

  @Test
  public void getChildNodes_ReturnsTwoNodes_WhenAtTopRight() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var node = new BoardAStarSearchNode(0, board, new Coordinate(16, 16), new HashSet<>(), null);

    assertEquals(2, node.getChildNodes().size());
  }

  @Test
  public void getChildNodes_ReturnsTwoNodes_WhenAtBottomMiddleAndBlockByWallAtTop() {
    var board = Board.from(new BoardSettings(9, PlayerCount.TWO));
    var wallOne = new WallPieceLocation(new Coordinate(8, 1),
        new Coordinate(9, 1),
        new Coordinate(10, 1));
    var wallTwo = new WallPieceLocation(new Coordinate(6, 1),
        new Coordinate(5, 1),
        new Coordinate(4, 1));
    board = board.placeWall(PlayerId.ONE, wallOne);
    board = board.placeWall(PlayerId.ONE, wallTwo);
    var node = new BoardAStarSearchNode(0, board, new Coordinate(16, 16), new HashSet<>(), null);

    assertEquals(2, node.getChildNodes().size());
  }
}
