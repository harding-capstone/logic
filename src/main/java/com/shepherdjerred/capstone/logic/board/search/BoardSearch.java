package com.shepherdjerred.capstone.logic.board.search;

import com.github.bentorfs.ai.search.asearch.AStarSearchNode;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import java.util.Set;

public interface BoardSearch {

  boolean hasPathToDestination(Board board, Coordinate source, Coordinate destination);

  boolean hasPathToAnyDestination(Board board, Coordinate source, Set<Coordinate> destinations);

  int getShortestPathToDestination(Board board, Coordinate source, Coordinate destination);

  int getShortestPathToAnyDestination(Board board, Coordinate source, Set<Coordinate> destinations);

  AStarSearchNode getPathToDestination(Board board, Coordinate source, Coordinate destination);

  AStarSearchNode getPathToAnyDestination(Board board, Coordinate source, Set<Coordinate> destination);
}
