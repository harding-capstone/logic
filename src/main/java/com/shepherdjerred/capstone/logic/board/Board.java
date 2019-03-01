package com.shepherdjerred.capstone.logic.board;

import com.google.common.base.Preconditions;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.exception.InvalidBoardTransformationException;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayoutBoardCellsInitializer;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Composes BoardLayout and BoardPieces to represent a Quoridor Board.
 */
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
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }
    if (!boardLayout.isPawn(destination) || !isEmpty(destination)) {
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
    var c1 = location.getC1();
    var vertex = location.getVertex();
    var c2 = location.getC2();

    Preconditions.checkArgument(isCoordinateValid(c1));
    Preconditions.checkArgument(isCoordinateValid(vertex));
    Preconditions.checkArgument(isCoordinateValid(c2));

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
