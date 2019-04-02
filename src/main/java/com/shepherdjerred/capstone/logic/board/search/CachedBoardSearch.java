package com.shepherdjerred.capstone.logic.board.search;

import com.github.bentorfs.ai.search.asearch.AStarSearchNode;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.QuoridorBoard;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CachedBoardSearch implements BoardSearch{

  private final BoardPathCache cache;
  private final BoardSearch boardSearch;

  @Override
  public boolean hasPathToDestination(QuoridorBoard board, Coordinate source,
      Coordinate destination) {
    if (cache.hasShortestPath()) {
      cache.getShortestPathForPlayer()
    } else {
      boardSearch.hasPathToDestination(board, source, destination);
    }
    return false;
  }

  @Override
  public boolean hasPathToAnyDestination(QuoridorBoard board, Coordinate source,
      Set<Coordinate> destinations) {
    return false;
  }

  @Override
  public int getShortestPathToDestination(QuoridorBoard board, Coordinate source,
      Coordinate destination) {
    return 0;
  }

  @Override
  public int getShortestPathToAnyDestination(QuoridorBoard board, Coordinate source,
      Set<Coordinate> destinations) {
    return 0;
  }

  @Override
  public AStarSearchNode getPathToDestination(QuoridorBoard board, Coordinate source,
      Coordinate destination) {
    return null;
  }

  @Override
  public AStarSearchNode getPathToAnyDestination(QuoridorBoard board, Coordinate source,
      Set<Coordinate> destination) {
    return null;
  }
}
