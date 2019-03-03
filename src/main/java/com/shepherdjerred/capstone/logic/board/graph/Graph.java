package com.shepherdjerred.capstone.logic.board.graph;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Graph {
  private final ImmutableSet<Node> nodes;
}
