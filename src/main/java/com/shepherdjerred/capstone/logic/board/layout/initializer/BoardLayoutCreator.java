package com.shepherdjerred.capstone.logic.board.layout.initializer;

import com.shepherdjerred.capstone.logic.board.layout.BoardCell;
import com.shepherdjerred.capstone.logic.board.BoardSettings;

public interface BoardLayoutCreator {

  /**
   * Creates a matrix of BoardLayout
   *
   * @param boardSettings Settings to use when creating the boardState
   * @return A matrix of BoardLayout based on the boardState settings
   */
  BoardCell[][] createBoardCells(BoardSettings boardSettings);
}
