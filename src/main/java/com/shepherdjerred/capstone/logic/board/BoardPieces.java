package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPieces {

  private final Map<Coordinate, Piece> pieces;
  private final Map<PlayerId, Coordinate> pawnLocations;
  private final int gridSize;

  public static BoardPieces from(BoardSettings boardSettings) {
    return from(boardSettings, new BoardPiecesInitializer());
  }

  public static BoardPieces from(BoardSettings boardSettings,
      BoardPiecesInitializer boardPiecesInitializer) {
    var gridSize = boardSettings.getGridSize();
    Map<PlayerId, Coordinate> pawnLocations = boardPiecesInitializer.initializePawnLocations(
        boardSettings);
    Map<Coordinate, Piece> pieces = boardPiecesInitializer.pawnLocationsToPieceLocations(
        pawnLocations);
    return new BoardPieces(pieces, pawnLocations, gridSize);
  }

  /**
   * Gets the location of the playerId's pawn.
   *
   * @param playerId The playerId to get the pawn Coordinates of
   * @return The coordinate of the playerId's pawn
   */
  public Coordinate getPawnLocation(PlayerId playerId) {
    return pawnLocations.get(playerId);
  }

  /**
   * Moves a pawn.
   *
   * @param playerId The owner of the pawn to move
   * @param destination The new location of the pawn
   * @return The BoardPieces after the move
   */
  public BoardPieces movePawn(PlayerId playerId, Coordinate destination) {
    var newPiecesMap = new HashMap<>(pieces);
    var newPawnLocations = new HashMap<>(pawnLocations);
    var originalPawnLocation = getPawnLocation(playerId);
    var originalPiece = pieces.get(originalPawnLocation);

    newPiecesMap.remove(originalPawnLocation);
    newPiecesMap.put(destination, originalPiece);
    newPawnLocations.put(playerId, destination);

    return new BoardPieces(newPiecesMap, newPawnLocations, gridSize);
  }

  /**
   * Places a wall.
   *
   * @param playerId The owner of the wall to place
   * @param c1 First coordinate of the wall
   * @param c2 Second coordinate of the wall
   * @return The BoardPieces after the move
   */
  public BoardPieces placeWall(PlayerId playerId, Coordinate c1, Coordinate vertex, Coordinate c2) {
    var newPiecesMap = new HashMap<>(pieces);
    newPiecesMap.put(c1, new WallPiece(playerId));
    newPiecesMap.put(vertex, new WallPiece(playerId));
    newPiecesMap.put(c2, new WallPiece(playerId));

    return new BoardPieces(newPiecesMap, pawnLocations, gridSize);
  }


  /**
   * Checks if a piece exists at a Coordinate.
   */
  public boolean hasPiece(Coordinate coordinate) {
    if (pieces.containsKey(coordinate)) {
      return pieces.get(coordinate) != NullPiece.INSTANCE;
    } else {
      return false;
    }
  }

  /**
   * Checks if a piece exists at a Coordinate.
   */
  public boolean isEmpty(Coordinate coordinate) {
    return !hasPiece(coordinate);
  }

  /**
   * Gets the piece at a Coordinate.
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
