package com.shepherdjerred.capstone.logic.board.initializer;

import com.shepherdjerred.capstone.logic.board.BoardCell;
import com.shepherdjerred.capstone.logic.board.BoardSettings;

public interface BoardInitializer {

  /**
   * Creates a matrix of BoardCells
   *
   * @param boardSettings Settings to use when creating the board
   * @return A matrix of BoardCells based on the board settings
   */
  BoardCell[][] createBoardCells(BoardSettings boardSettings);
}
