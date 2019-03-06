package com.shepherdjerred.capstone.logic.board.search;

import static org.junit.Assert.assertTrue;

import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class AStarBoardSearchTest {

  @Test
  public void hasPathToAnyDestination_returnTrue_whenFindingPathTwoDifferentDiagonalSidesOnOpenBoard() {
    var board = QuoridorBoard.from(new BoardSettings(9, PlayerCount.TWO));
    var search = new AStarBoardSearch();
    var src = new Coordinate(0, 0);
    Set<Coordinate> dest = new HashSet<>();
    dest.add(new Coordinate(16, 16));

    assertTrue(search.hasPathToAnyDestination(board, src, dest));
  }

  @Test
  public void hasPathToAnyDestination_returnTrue_whenFindingPathTwoDifferentCardinalSidesOnOpenBoard() {
    var board = QuoridorBoard.from(new BoardSettings(9, PlayerCount.TWO));
    var search = new AStarBoardSearch();
    var src = new Coordinate(8, 0);
    Set<Coordinate> dest = new HashSet<>();
    dest.add(new Coordinate(0, 8));

    assertTrue(search.hasPathToAnyDestination(board, src, dest));
  }}
