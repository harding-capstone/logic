package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.exception.InvalidBoardTransformationException;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * Represents the positions that Pieces exist on the game board.
 */
// TODO add a graph representation
@ToString
@EqualsAndHashCode
public final class Board {

  private final BoardLayout boardLayout;
  private final BoardPieces boardPieces;

  // This works but could make us create an invalid match state.. but that is probably outside of the scope of the class
  public static Board createBoard(BoardLayout boardLayout,
      BoardPieces boardPieces) {
    if (!boardLayout.getBoardSettings().equals(boardPieces.getBoardSettings())) {
      throw new IllegalArgumentException(
          "Board settings must match between layout and piece locations");
    }

    return new Board(boardLayout, boardPieces);
  }

  /**
   * Private constructor used to update the object.
   */
  private Board(BoardLayout boardLayout, BoardPieces boardPieces) {
    this.boardLayout = boardLayout;
    this.boardPieces = boardPieces;
  }

  public BoardSettings getBoardSettings() {
    return boardLayout.getBoardSettings();
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
  // TODO better validation (return error messages)
  // TODO extract validation
  public Board movePawn(PlayerId playerId, Coordinate destination) {
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }
    if (!boardLayout.isPawnBoardCell(destination) && !isEmpty(destination)) {
      throw new InvalidBoardTransformationException();
    }

    var newPiecesLocationTracker = boardPieces.movePawn(playerId, destination);
    return new Board(boardLayout, newPiecesLocationTracker);
  }


  /**
   * Places a wall.
   *
   * @param playerId The owner of the wall to place
   * @param c1 First coordinate of the wall
   * @param c2 Second coordinate of the wall
   * @return The BoardPieces after the move
   */
  // TODO better validation (return error messages)
  // TODO extract validation
  public Board placeWall(PlayerId playerId, Coordinate c1, Coordinate c2) {
    if (boardLayout.isCoordinateInvalid(c1)) {
      throw new CoordinateOutOfBoundsException(c1);
    }
    if (boardLayout.isCoordinateInvalid(c2)) {
      throw new CoordinateOutOfBoundsException(c2);
    }
    if (!boardLayout.isWallBoardCell(c1) || !boardLayout.isWallBoardCell(c2) || !isEmpty(c1)
        || !isEmpty(c2)) {
      throw new InvalidBoardTransformationException();
    }

    var newPieceLocationTracker = boardPieces.placeWall(playerId, c1, c2);
    return new Board(boardLayout, newPieceLocationTracker);
  }

  public BoardCell getBoardCell(Coordinate coordinate) {
    return boardLayout.getBoardCell(coordinate);
  }

  public boolean isPawnBoardCell(Coordinate coordinate) {
    return boardLayout.isPawnBoardCell(coordinate);
  }

  public boolean isWallBoardCell(Coordinate coordinate) {
    return boardLayout.isWallBoardCell(coordinate);
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
    return boardPieces.isEmpty(coordinate);
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
