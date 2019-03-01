package com.shepherdjerred.capstone.logic.board;

import com.google.common.base.Preconditions;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.exception.InvalidBoardTransformationException;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayoutBoardCellsInitializer;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Composes BoardLayout and BoardPieces to represent a Quoridor Board.
 */
// TODO this is a hybrid data structure/object. should be refactored.
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

  private final BoardLayout boardLayout;
  private final BoardPieces boardPieces;
  @Getter
  private final BoardSettings boardSettings;

  public static Board from(BoardSettings boardSettings) {
    return from(boardSettings,
        new BoardLayoutBoardCellsInitializer(),
        new BoardPiecesInitializer());
  }

  /**
   * Creates a new Board.
   */
  public static Board from(BoardSettings boardSettings,
      BoardLayoutBoardCellsInitializer boardLayoutBoardCellsInitializer,
      BoardPiecesInitializer boardPiecesInitializer) {
    var layout = BoardLayout.from(boardSettings, boardLayoutBoardCellsInitializer);
    var pieces = BoardPieces.from(boardSettings, boardPiecesInitializer);
    return new Board(layout, pieces, boardSettings);
  }

  /**
   * Gets the location of the playerId's pawn.
   *
   * @param playerId The playerId to get the pawn Coordinates of
   * @return The coordinate of the playerId's pawn
   */
  public Coordinate getPawnLocation(PlayerId playerId) {
    return boardPieces.getPawnLocation(playerId);
  }

  public Set<Coordinate> getPawnLocations() {
    return boardPieces.getPawnLocations();
  }

  public Set<Coordinate> getWallLocations() {
    return boardPieces.getWallLocations();
  }

  public Set<Coordinate> getAdjacentPawnSpaces(Coordinate coordinate) {
    Preconditions.checkArgument(isPawnBoardCell(coordinate));
    Set<Coordinate> spaces = new HashSet<>();
    var gridSize = boardSettings.getGridSize();
    int x = coordinate.getX();
    int y = coordinate.getY();

    if (x > 0) {
      spaces.add(coordinate.toLeft(2));
    }

    if (x < gridSize - 1) {
      spaces.add(coordinate.toRight(2));
    }

    if (y > 0) {
      spaces.add(coordinate.below(2));
    }

    if (y < gridSize - 1) {
      spaces.add(coordinate.above(2));
    }

    return spaces;
  }

  /**
   * Moves a pawn.
   *
   * @param playerId The owner of the pawn to move
   * @param destination The new location of the pawn
   * @return The BoardPieces after the move
   */
  // TODO better validation (return error messages?)
  // TODO extract validation
  public Board movePawn(PlayerId playerId, Coordinate destination) {
    if (isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }
    if (!isPawnBoardCell(destination) || !isEmpty(destination)) {
      throw new InvalidBoardTransformationException();
    }

    var newBoardPieces = boardPieces.movePawn(playerId, destination);
    return new Board(boardLayout, newBoardPieces, boardSettings);
  }

  /**
   * Places a wall.
   */
  // TODO better validation (return error messages?)
  // TODO extract validation
  public Board placeWall(PlayerId playerId, WallPieceLocation location) {
    var c1 = location.getFirstCoordinate();
    var vertex = location.getVertex();
    var c2 = location.getSecondCoordinate();

    if (isCoordinateInvalid(c1)) {
      throw new CoordinateOutOfBoundsException(c1);
    }
    if (isCoordinateInvalid(vertex)) {
      throw new CoordinateOutOfBoundsException(vertex);
    }
    if (isCoordinateInvalid(c2)) {
      throw new CoordinateOutOfBoundsException(c2);
    }

    if (!boardLayout.isWall(c1)
        || !boardLayout.isWallVertex(vertex)
        || !boardLayout.isWall(c2)
        || !isEmpty(c1)
        || !isEmpty(vertex)
        || !isEmpty(c2)) {
      throw new InvalidBoardTransformationException();
    }

    var newBoardPieces = boardPieces.placeWall(playerId, c1, vertex, c2);
    return new Board(boardLayout, newBoardPieces, boardSettings);
  }

  public BoardCell getBoardCell(Coordinate coordinate) {
    return boardLayout.getBoardCell(coordinate);
  }

  public boolean isPawnBoardCell(Coordinate coordinate) {
    return boardLayout.isPawn(coordinate);
  }

  public boolean isWallBoardCell(Coordinate coordinate) {
    return boardLayout.isWall(coordinate);
  }

  public boolean isWallVertex(Coordinate coordinate) {
    return boardLayout.isWallVertex(coordinate);
  }

  public boolean isCoordinateValid(Coordinate coordinate) {
    return boardLayout.isCoordinateValid(coordinate);
  }

  public boolean isCoordinateInvalid(Coordinate coordinate) {
    return boardLayout.isCoordinateInvalid(coordinate);
  }

  /**
   * Checks if a piece exists at a Coordinate.
   */
  public boolean hasPiece(Coordinate coordinate) {
    return boardPieces.hasPiece(coordinate);
  }

  /**
   * Checks if a piece exists at a Coordinate.
   */
  public boolean isEmpty(Coordinate coordinate) {
    return !hasPiece(coordinate);
  }

  public boolean hasWall(Coordinate coordinate) {
    return isWallBoardCell(coordinate) || isWallVertex(coordinate) && hasPiece(
        coordinate);
  }

  /**
   * Gets the piece at a Coordinate.
   *
   * @param coordinate The Coordinate to get the Piece from
   * @return The Piece at the Coordinate, or a NullPiece if there is none
   */
  public Piece getPiece(Coordinate coordinate) {
    return boardPieces.getPiece(coordinate);
  }
}
