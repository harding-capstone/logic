package com.shepherdjerred.capstone.logic.board.graph;

import com.google.common.collect.ImmutableList;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Node {
  private final Coordinate location;
  private final ImmutableList<Node> getChildren;

  public Node from(Board board) {
    return null;
  }
}
