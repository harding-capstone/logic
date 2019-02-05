package com.shepherdjerred.capstone.logic.logic;

import com.shepherdjerred.capstone.logic.BoardSettings;
import com.shepherdjerred.capstone.logic.BoardSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardInitializer;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import org.junit.Test;

public class BoardTest {
  @Test
  public void doThing() {
    BoardSettings boardSettings = new BoardSettings(PlayerCount.TWO, 9);
    Board board = BoardInitializer.INSTANCE.createBoard(boardSettings);

    Turn firstTurn = new MovePawnTurn(Player.ONE, new Coordinate(9 , 1), new Coordinate(9, 2));
    Turn secondTurn = new PlaceWallTurn(Player.TWO, new Coordinate(10, 1), new Coordinate(10, 2));

    try {
      Board newBoard = board.doTurn(firstTurn);
      Board second = newBoard.doTurn(secondTurn);

      System.out.println(board.toFormattedString());
      System.out.println(newBoard.toFormattedString());
      System.out.println(second.toFormattedString());
    } catch (InvalidTurnException e) {
      e.printStackTrace();
    }
  }
}
