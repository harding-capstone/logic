package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.board.pieces.PieceLocations;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.piece.Piece;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class Board {

  private final BoardLayout boardLayout;
  private final PieceLocations pieceLocations;

  public Board(MatchSettings matchSettings) {
    boardLayout = new BoardLayout(matchSettings.getBoardSettings());
    pieceLocations = new PieceLocations(boardLayout, matchSettings);
  }

  private Board(BoardLayout boardLayout, PieceLocations pieceLocations) {
    this.boardLayout = boardLayout;
    this.pieceLocations = pieceLocations;
  }

  public boolean hasPiece(Coordinate coordinate) {
    return pieceLocations.hasPiece(coordinate);
  }

  public boolean isEmpty(Coordinate coordinate) {
    return pieceLocations.isEmpty(coordinate);
  }

  public Piece getPiece(Coordinate coordinate) {
    return pieceLocations.getPiece(coordinate);
  }

  public BoardCell getBoardCell(Coordinate coordinate) {
    return boardLayout.getCell(coordinate);
  }

  public boolean isWallCell(Coordinate coordinate) {
    return boardLayout.getCell(coordinate) == BoardCell.WALL;
  }

  public Board setPawnPosition(Player player, Coordinate source, Coordinate destination) {
    var newPieceLocations = pieceLocations.movePawn(player, source, destination);
    return buildBoardStateWithNewPieceLocations(newPieceLocations);
  }

  public Board setWallPiece(Player player, Coordinate c1, Coordinate c2) {
    var newPieceLocations = pieceLocations.placeWall(player, c1, c2);
    return buildBoardStateWithNewPieceLocations(newPieceLocations);
  }

  private Board buildBoardStateWithNewPieceLocations(PieceLocations pieceLocations) {
    return new Board(boardLayout, pieceLocations);
  }
}
