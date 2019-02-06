package com.shepherdjerred.capstone.logic.board;

import com.google.common.collect.ImmutableMap;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.exception.CoordinateOutOfBoundsException;
import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.board.layout.initializer.BoardLayoutCreator;
import com.shepherdjerred.capstone.logic.piece.NullPiece;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import com.shepherdjerred.capstone.logic.piece.Piece;
import com.shepherdjerred.capstone.logic.piece.WallPiece;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class BoardState {

  private final BoardLayout boardLayout;
  private final ImmutableMap<Coordinate, Piece> pieces;
  private final ImmutableMap<Player, Coordinate> pawnLocations;

  public BoardState(BoardLayoutCreator boardLayoutCreator, BoardSettings boardSettings) {
    boardLayout = new BoardLayout(boardSettings, boardLayoutCreator);
    pieces = ImmutableMap.<Coordinate, Piece>builder().build();
    pawnLocations = ImmutableMap.<Player, Coordinate>builder().build();
  }

  public BoardCell getBoardCell(Coordinate coordinate) {
    return boardLayout.getCell(coordinate);
  }

  public boolean hasPiece(Coordinate coordinate) {
    if (boardLayout.isCoordinateInvalid(coordinate)) {
      throw new CoordinateOutOfBoundsException(coordinate);
    }

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

  public BoardState setPawnPosition(Player player, Coordinate destination) {
    if (boardLayout.isCoordinateInvalid(destination)) {
      throw new CoordinateOutOfBoundsException(destination);
    }

    // TODO Set pawn position and return boardstate
    return this;
  }

  public BoardState setWallPiece(Player player, Coordinate c1, Coordinate c2) {
    if (boardLayout.isCoordinateInvalid(c1)) {
      throw new CoordinateOutOfBoundsException(c1);
    }
    if (boardLayout.isCoordinateInvalid(c2)) {
      throw new CoordinateOutOfBoundsException(c2);
    }

    // TODO Set wall piece and return boardstate
    return this;
  }

  private boolean doesPieceMatchBoardCell(Piece piece, Coordinate destination) {
    if (pieces.containsKey(destination)) {
      return false;
    }

    var cell = getBoardCell(destination);
    if (piece instanceof PawnPiece) {
      return cell == BoardCell.PAWN;
    } else if (piece instanceof WallPiece) {
      return cell == BoardCell.WALL;
    } else {
      throw new IllegalArgumentException();
    }
  }

}
