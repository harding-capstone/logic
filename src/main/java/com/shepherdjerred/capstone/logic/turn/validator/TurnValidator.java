package com.shepherdjerred.capstone.logic.turn.validator;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.Turn;

public interface TurnValidator {
  boolean isTurnValid(Turn turn, Board board);
}
