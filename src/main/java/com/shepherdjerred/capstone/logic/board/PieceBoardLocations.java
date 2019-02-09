package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import com.shepherdjerred.capstone.logic.player.Player;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class PieceBoardLocations {

  private final Map<Coordinate, Piece> pieces;
  private final Map<Player, Coordinate> pawnLocations;

  public PieceBoardLocations() {
    pieces = new HashMap<>();
    pawnLocations = new HashMap<>();
  }

  public static PieceBoardLocations initializePieceLocations(BoardSettings boardSettings,
      PieceInitializer pieceInitializer) {
    Map<Player, Coordinate> pawnLocations = pieceInitializer.initializePawnLocations(boardSettings);
    Map<Coordinate, Piece> pieces = pieceInitializer.pawnLocationsToPieceLocations(pawnLocations);
    return new PieceBoardLocations(pieces, pawnLocations);
  }

  public PieceBoardLocations(Map<Coordinate, Piece> pieces,
      Map<Player, Coordinate> pawnLocations) {
    this.pieces = pieces;
    this.pawnLocations = pawnLocations;
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
  public PieceBoardLocations movePawn(Player player, Coordinate destination) {
    var newPiecesMap = new HashMap<>(pieces);
    var newPawnLocations = new HashMap<>(pawnLocations);
    var originalPawnLocation = getPawnLocation(player);
    var originalPiece = pieces.get(originalPawnLocation);

    newPiecesMap.remove(originalPawnLocation);
    newPiecesMap.put(destination, originalPiece);
    newPawnLocations.put(player, destination);

    return new PieceBoardLocations(newPiecesMap, newPawnLocations);
  }

  /**
   * Places a wall
   *
   * @param player The owner of the wall to place
   * @param c1 First coordinate of the wall
   * @param c2 Second coordinate of the wall
   * @return The BoardPieces after the move
   */
  public PieceBoardLocations placeWall(Player player, Coordinate c1, Coordinate c2) {
    var newPiecesMap = new HashMap<>(pieces);
    newPiecesMap.put(c1, new WallPiece(player));
    newPiecesMap.put(c2, new WallPiece(player));

    return new PieceBoardLocations(newPiecesMap, pawnLocations);
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
