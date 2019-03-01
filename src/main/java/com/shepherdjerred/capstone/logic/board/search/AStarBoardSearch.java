package com.shepherdjerred.capstone.logic.board.search;

import com.github.bentorfs.ai.search.asearch.ASearchAlgorithm;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import java.util.Set;

public class AStarBoardSearch implements BoardSearch {

  @Override
  public boolean hasPathToAnyDestination(Board board,
      Coordinate source,
      Set<Coordinate> destinations) {
    var algorithm = new ASearchAlgorithm();
    var root = new BoardAStarSearchNode(0, board, source, destinations);
    var solution = algorithm.searchSolution(root);
    return solution != null;
  }

  @Override
  public int getShortestPathToAnyDestination(Board board,
      Coordinate source,
      Set<Coordinate> destinations) {
    var algorithm = new ASearchAlgorithm();
    var root = new BoardAStarSearchNode(0, board, source, destinations);
    var solution = algorithm.searchSolution(root);
    return (int) solution.getValue();
  }
}
