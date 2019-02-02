package com.shepherdjerred.capstone.logic.turn;

import com.shepherdjerred.capstone.logic.Cell;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.piece.PawnPiece;
import lombok.Data;

@Data
public class MovePawnTurn implements Turn {
  private Player invoker;
  private Cell from;
  private Cell to;
  private PawnPiece piece;
}
