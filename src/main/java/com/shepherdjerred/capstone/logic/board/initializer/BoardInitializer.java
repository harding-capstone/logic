package com.shepherdjerred.capstone.logic.board.initializer;

import com.shepherdjerred.capstone.logic.board.BoardCell;
import com.shepherdjerred.capstone.logic.board.BoardSettings;

public interface BoardInitializer {
  BoardCell[][] createBoardCells(BoardSettings boardSettings);
}
