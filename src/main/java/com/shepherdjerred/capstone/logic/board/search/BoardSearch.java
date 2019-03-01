package com.shepherdjerred.capstone.logic.board.search;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import java.util.Set;

public interface BoardSearch {

  boolean hasPathToAnyDestination(Board board, Coordinate source, Set<Coordinate> destinations);

  int getShortestPathToAnyDestination(Board board, Coordinate source, Set<Coordinate> destinations);
}
