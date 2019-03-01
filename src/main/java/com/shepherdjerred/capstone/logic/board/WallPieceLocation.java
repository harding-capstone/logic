package com.shepherdjerred.capstone.logic.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class WallPieceLocation {

  private final Coordinate c1;
  private final Coordinate vertex;
  private final Coordinate c2;


  public WallPieceLocation(Coordinate c1,
      Coordinate vertex,
      Coordinate c2) {
    this.c1 = c1;
    this.vertex = vertex;
    this.c2 = c2;
    if (!isInvalid()) {
      throw new IllegalStateException();
    }
  }

  private boolean isInvalid() {
    return !areCoordinatesAdjacentToVertex() || !areCoordinatesInStraightLine();
  }

  private boolean areCoordinatesAdjacentToVertex() {
    return c1.isAdjacent(vertex) && c2.isAdjacent(vertex);
  }

  private boolean areCoordinatesInStraightLine() {
    return (c1.getX() == vertex.getX() && c2.getX() == vertex.getX())
        || (c1.getY() == vertex.getY() && c2.getY() == vertex.getY());
  }
}
