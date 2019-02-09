package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.exception.InvalidBoardTransformationException;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.player.Player;
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
  private final PieceBoardLocations pieceBoardLocations;

  public static Board createBoard(BoardLayout boardLayout,
      PieceBoardLocations pieceBoardLocations) {
    // TODO validate that boardLayout and pieceBoardLocations are compatible (grid size check)
    return new Board(boardLayout, pieceBoardLocations);
  }

  /**
   * Private constructor used to update the object
   */
  private Board(BoardLayout boardLayout, PieceBoardLocations pieceBoardLocations) {
    this.boardLayout = boardLayout;
    this.pieceBoardLocations = pieceBoardLocations;
  }

  public BoardSettings getBoardSettings() {
    return boardLayout.getBoardSettings();
  }

  /**
   * Gets the location of the player's pawn
   *
   * @param player The player to get the pawn Coordinates of
   * @return The coordinate of the player's pawn
   */
  public Coordinate getPawnLocation(Player player) {
    return pieceBoardLocations.getPawnLocation(player);
  }


  /**
   * Moves a pawn
   *
   * @param player The owner of the pawn to move
   * @param destination The new location of the pawn
   * @return The BoardPieces after the move
   */
  // TODO better validation (return error messages)
  // TODO extract validation
  public Board movePawn(Player player, Coordinate destination) {
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }
    if (!boardLayout.isPawnBoardCell(destination) && !isEmpty(destination)) {
      throw new InvalidBoardTransformationException();
    }

    var newPiecesLocationTracker = pieceBoardLocations.movePawn(player, destination);
    return new Board(boardLayout, newPiecesLocationTracker);
  }


  /**
   * Places a wall
   *
   * @param player The owner of the wall to place
   * @param c1 First coordinate of the wall
   * @param c2 Second coordinate of the wall
   * @return The BoardPieces after the move
   */
  // TODO better validation (return error messages)
  // TODO extract validation
  public Board placeWall(Player player, Coordinate c1, Coordinate c2) {
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

    var newPieceLocationTracker = pieceBoardLocations.placeWall(player, c1, c2);
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
   * Checks if a piece exists at a Coordinate
   */
  public boolean hasPiece(Coordinate coordinate) {
    return pieceBoardLocations.hasPiece(coordinate);
  }

  /**
   * Checks if a piece exists at a Coordinate
   */
  public boolean isEmpty(Coordinate coordinate) {
    return pieceBoardLocations.isEmpty(coordinate);
  }

  /**
   * Gets the piece at a Coordinate
   *
   * @param coordinate The Coordinate to get the Piece from
   * @return The Piece at the Coordinate, or a NullPiece if there is none
   */
  public Piece getPiece(Coordinate coordinate) {
    return pieceBoardLocations.getPiece(coordinate);
  }
}
