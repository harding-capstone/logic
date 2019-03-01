package com.shepherdjerred.capstone.logic.board.search;

import com.github.bentorfs.ai.common.TreeNode;
import com.github.bentorfs.ai.search.asearch.AStarSearchNode;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(exclude = {"board", "goals"})
@EqualsAndHashCode
@AllArgsConstructor
public class BoardAStarSearchNode extends AStarSearchNode {

  private final int cost;
  private final Board board;
  private final Coordinate location;
  private final Set<Coordinate> goals;

  @Override
  public List<TreeNode> getChildNodes() {
    return board.getAdjacentPawnSpaces(location)
        .stream()
        .filter(coordinate -> {
          var midpoint = Coordinate.calculateMidpoint(location, coordinate);
          return board.hasWall(midpoint);
        })
        .map(space -> new BoardAStarSearchNode(cost + 1, board, space, goals))
        .collect(Collectors.toList());
  }

  @Override
  public boolean isSolutionNode() {
    return goals.contains(location);
  }

  @Override
  public int getCostSoFar() {
    return cost;
  }

  @Override
  public int getEstimatedCostToSolution() {
    return getDistanceToNearestGoal(location, goals) + 1;
  }

  @Override
  public boolean isSamePosition(AStarSearchNode node) {
    if (node instanceof BoardAStarSearchNode) {
      return location.equals(((BoardAStarSearchNode) node).location);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private int getDistanceToNearestGoal(Coordinate coordinate, Set<Coordinate> goals) {
    return goals.stream()
        .map(goal -> Coordinate.calculateManhattanDistance(coordinate, goal))
        .min(Integer::compareTo)
        .orElseThrow(IllegalStateException::new);
  }
}
