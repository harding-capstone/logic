package com.shepherdjerred.capstone.logic.board.pieces;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PieceLocations {

  private final BoardLayout boardLayout;
  private final MatchSettings matchSettings;
  private final Map<Coordinate, Piece> pieces;
  private final Map<Player, Coordinate> pawnLocations;

  public PieceLocations(BoardLayout boardLayout, MatchSettings matchSettings) {
    this.boardLayout = boardLayout;
    this.matchSettings = matchSettings;
    this.pieces = new HashMap<>();
    this.pawnLocations = new HashMap<>();
    initializePiecesAndPawnLocations();
  }

  private PieceLocations(
      BoardLayout boardLayout,
      MatchSettings matchSettings,
      Map<Coordinate, Piece> pieces,
      Map<Player, Coordinate> pawnLocations) {
    this.boardLayout = boardLayout;
    this.matchSettings = matchSettings;
    this.pieces = pieces;
    this.pawnLocations = pawnLocations;
  }

  public Coordinate getPawnLocation(Player player) {
    return pawnLocations.get(player);
  }

  public PieceLocations movePawn(Player player, Coordinate source, Coordinate destination) {
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }

    var newPiecesMap = new HashMap<>(pieces);
    var newPawnLocations = new HashMap<>(pawnLocations);
    var originalPiece = pieces.get(source);

    newPiecesMap.remove(source);
    newPiecesMap.put(destination, originalPiece);
    newPawnLocations.put(player, destination);

    return new PieceLocations(boardLayout, matchSettings, newPiecesMap, newPawnLocations);
  }

  public PieceLocations placeWall(Player player, Coordinate c1, Coordinate c2) {
    if (boardLayout.isCoordinateInvalid(c1)) {
      throw new CoordinateOutOfBoundsException(c1);
    }
    if (boardLayout.isCoordinateInvalid(c2)) {
      throw new CoordinateOutOfBoundsException(c2);
    }

    var newPiecesMap = new HashMap<>(pieces);
    newPiecesMap.put(c1, new WallPiece(player));
    newPiecesMap.put(c2, new WallPiece(player));

    return new PieceLocations(boardLayout, matchSettings, newPiecesMap, pawnLocations);
  }

  public boolean hasPiece(Coordinate coordinate) {
    return pieces.containsKey(coordinate);
  }

  public boolean isEmpty(Coordinate coordinate) {
    return !hasPiece(coordinate);
  }

  public Piece getPiece(Coordinate coordinate) {
    if (hasPiece(coordinate)) {
      return pieces.get(coordinate);
    } else {
      return NullPiece.INSTANCE;
    }
  }

  private void initializePiecesAndPawnLocations() {
    var playerCount = matchSettings.getPlayerCount();
    var gridSize = boardLayout.getBoardSettings().getGridSize();
    var midpoint = gridSize / 2;

    Map<Player, Coordinate> pawns = new HashMap<>();

    var playerOnePawn = new Coordinate(midpoint, 0);
    var playerTwoPawn = new Coordinate(midpoint, gridSize - 1);

    pawns.put(Player.ONE, playerOnePawn);
    pawns.put(Player.TWO, playerTwoPawn);

    if (playerCount == PlayerCount.FOUR) {
      var playerThreePawn = new Coordinate(0, midpoint);
      var playerFourPawn = new Coordinate(gridSize - 1, midpoint);

      pawns.put(Player.THREE, playerThreePawn);
      pawns.put(Player.FOUR, playerFourPawn);
    }

    pawns.forEach((player, coordinate) -> pieces.put(coordinate, new PawnPiece(player)));

    pawnLocations.putAll(pawns);
  }
}
