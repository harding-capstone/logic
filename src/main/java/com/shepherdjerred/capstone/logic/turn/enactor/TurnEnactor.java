package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnEnactor {
  Board enactTurn(Turn turn, Board board);
}
