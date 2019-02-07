package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.exception.InvalidBoardTransformationException;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
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
  private final PlayerCount playerCount;
  private final Map<Coordinate, Piece> pieces;
  private final Map<Player, Coordinate> pawnLocations;

  /**
   * Creates a new BoardPieces object intended for use in a new game. Pawns will be placed in the
   * standard positions.
   *
   * @param boardSettings The BoardSettings to use when creating the board.
   * @param playerCount The number of players on the board. Used to create initial pawns.
   */
  public Board(BoardSettings boardSettings, PlayerCount playerCount) {
    this.boardLayout = new BoardLayout(boardSettings);
    this.playerCount = playerCount;
    this.pieces = new HashMap<>();
    this.pawnLocations = new HashMap<>();
    initializePiecesAndPawnLocations();
  }

  /**
   * Private constructor used to update the object
   */
  private Board(
      BoardLayout boardLayout,
      PlayerCount playerCount,
      Map<Coordinate, Piece> pieces,
      Map<Player, Coordinate> pawnLocations) {
    this.boardLayout = boardLayout;
    this.playerCount = playerCount;
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
  public Board movePawn(Player player, Coordinate destination) {
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }
    if (!boardLayout.isPawnCell(destination) && !isEmpty(destination)) {
      throw new InvalidBoardTransformationException();
    }

    var newPiecesMap = new HashMap<>(pieces);
    var newPawnLocations = new HashMap<>(pawnLocations);
    var originalPawnLocation = getPawnLocation(player);
    var originalPiece = pieces.get(originalPawnLocation);

    newPiecesMap.remove(originalPawnLocation);
    newPiecesMap.put(destination, originalPiece);
    newPawnLocations.put(player, destination);

    return new Board(boardLayout, playerCount, newPiecesMap, newPawnLocations);
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
    if (!boardLayout.isWallCell(c1) || !boardLayout.isWallCell(c2) || !isEmpty(c1) || !isEmpty(c2)) {
      throw new InvalidBoardTransformationException();
    }

    var newPiecesMap = new HashMap<>(pieces);
    newPiecesMap.put(c1, new WallPiece(player));
    newPiecesMap.put(c2, new WallPiece(player));

    return new Board(boardLayout, playerCount, newPiecesMap, pawnLocations);
  }

  public BoardCell getCell(Coordinate coordinate) {
    return boardLayout.getCell(coordinate);
  }

  public boolean isPawnCell(Coordinate coordinate) {
    return boardLayout.isPawnCell(coordinate);
  }

  public boolean isWallCell(Coordinate coordinate) {
    return boardLayout.isWallCell(coordinate);
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

  /**
   * Initialize pawn pieces for the players
   */
  private void initializePiecesAndPawnLocations() {
    var gridSize = boardLayout.getBoardSettings().getGridSize();

    Map<Player, Coordinate> pawns = new HashMap<>();

    pawns.put(Player.ONE, getStartingPawnCoordinateForPlayerOne(gridSize));
    pawns.put(Player.TWO, getStartingPawnCoordinateForPlayerTwo(gridSize));

    if (playerCount == PlayerCount.FOUR) {
      pawns.put(Player.THREE, getStartingPawnCoordinateForPlayerThree(gridSize));
      pawns.put(Player.FOUR, getStartingPawnCoordinateForPlayerFour(gridSize));
    }

    pawns.forEach((player, coordinate) -> pieces.put(coordinate, new PawnPiece(player)));

    pawnLocations.putAll(pawns);
  }

  private Coordinate getStartingPawnCoordinateForPlayerOne(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(midpoint, 0);
  }

  private Coordinate getStartingPawnCoordinateForPlayerTwo(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(midpoint, gridSize - 1);
  }

  private Coordinate getStartingPawnCoordinateForPlayerThree(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(0, midpoint);
  }

  private Coordinate getStartingPawnCoordinateForPlayerFour(int gridSize) {
    var midpoint = gridSize / 2;
    return new Coordinate(gridSize - 1, midpoint);
  }
}
