package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.exception.InvalidBoardTransformationException;
import com.shepherdjerred.capstone.logic.board.exception.PieceInitializer;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import com.shepherdjerred.capstone.logic.player.Player;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents the positions that Pieces exist on the game board.
 */
@ToString
@EqualsAndHashCode
public final class Board {

  private final BoardLayout boardLayout;
  private final Map<Coordinate, Piece> pieces;
  private final Map<Player, Coordinate> pawnLocations;

  public static Board createNewBoard(BoardLayout boardLayout, BoardSettings boardSettings) {
    Map<Player, Coordinate> pawnLocations = PieceInitializer.initializePawnLocations(boardSettings);
    Map<Coordinate, Piece> pieces = PieceInitializer.initializePieces(pawnLocations);
    return new Board(boardLayout, pieces, pawnLocations);
  }

  /**
   * Private constructor used to update the object
   */
  private Board(
      BoardLayout boardLayout,
      Map<Coordinate, Piece> pieces,
      Map<Player, Coordinate> pawnLocations) {
    this.boardLayout = boardLayout;
    this.pieces = pieces;
    this.pawnLocations = pawnLocations;
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
    return pawnLocations.get(player);
  }

  /**
   * Moves a pawn
   *
   * @param player The owner of the pawn to move
   * @param destination The new location of the pawn
   * @return The BoardPieces after the move
   */
  public Board movePawn(Player player, Coordinate destination) {
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }
    if (!boardLayout.isPawnBoardCell(destination) && !isEmpty(destination)) {
      throw new InvalidBoardTransformationException();
    }

    var newPiecesMap = new HashMap<>(pieces);
    var newPawnLocations = new HashMap<>(pawnLocations);
    var originalPawnLocation = getPawnLocation(player);
    var originalPiece = pieces.get(originalPawnLocation);

    newPiecesMap.remove(originalPawnLocation);
    newPiecesMap.put(destination, originalPiece);
    newPawnLocations.put(player, destination);

    return new Board(boardLayout, newPiecesMap, newPawnLocations);
  }

  /**
   * Places a wall
   *
   * @param player The owner of the wall to place
   * @param c1 First coordinate of the wall
   * @param c2 Second coordinate of the wall
   * @return The BoardPieces after the move
   */
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

    var newPiecesMap = new HashMap<>(pieces);
    newPiecesMap.put(c1, new WallPiece(player));
    newPiecesMap.put(c2, new WallPiece(player));

    return new Board(boardLayout, newPiecesMap, pawnLocations);
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
    return pieces.containsKey(coordinate);
  }

  /**
   * Checks if a piece exists at a Coordinate
   */
  public boolean isEmpty(Coordinate coordinate) {
    return !hasPiece(coordinate);
  }

  /**
   * Gets the piece at a Coordinate
   *
   * @param coordinate The Coordinate to get the Piece from
   * @return The Piece at the Coordinate, or a NullPiece if there is none
   */
  public Piece getPiece(Coordinate coordinate) {
    if (hasPiece(coordinate)) {
      return pieces.get(coordinate);
    } else {
      return NullPiece.INSTANCE;
    }
  }
}
