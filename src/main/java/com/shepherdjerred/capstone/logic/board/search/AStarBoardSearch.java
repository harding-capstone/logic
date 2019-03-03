package com.shepherdjerred.capstone.logic.board.search;

import com.github.bentorfs.ai.search.asearch.ASearchAlgorithm;
import com.github.bentorfs.ai.search.asearch.AStarSearchNode;
import com.google.common.collect.ImmutableSet;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AStarBoardSearch implements BoardSearch {

  private final ASearchAlgorithm algorithm = new ASearchAlgorithm();

  @Override
  public boolean hasPathToDestination(Board board, Coordinate source, Coordinate destination) {
    return getPathToAnyDestination(board, source, ImmutableSet.of(destination)) != null;
  }

  @Override
  public boolean hasPathToAnyDestination(Board board,
      Coordinate source,
      Set<Coordinate> destinations) {
    return getPathToAnyDestination(board, source, destinations) != null;
  }

  @Override
  public int getShortestPathToDestination(Board board, Coordinate source, Coordinate destination) {
    return (int) getPathToAnyDestination(board, source, ImmutableSet.of(destination)).getValue();
  }

  @Override
  public int getShortestPathToAnyDestination(Board board,
      Coordinate source,
      Set<Coordinate> destinations) {
    return (int) getPathToAnyDestination(board, source, destinations).getValue();
  }

  @Override
  public AStarSearchNode getPathToDestination(Board board,
      Coordinate source,
      Coordinate destination) {
    var root = new BoardAStarSearchNode(0,
        board,
        source,
        ImmutableSet.of(destination),
        null);
    return algorithm.searchSolution(root);
  }

  @Override
  public AStarSearchNode getPathToAnyDestination(Board board,
      Coordinate source,
      Set<Coordinate> destinations) {
    var root = new BoardAStarSearchNode(0,
        board,
        source,
        destinations,
        null);
    return algorithm.searchSolution(root);
  }

}
