package edu.harding.capstone;

import edu.harding.capstone.action.Action;
import edu.harding.capstone.exceptions.InvalidActionException;
import java.util.HashMap;

public class Game {
  private final Board board;
  private final HashMap<Integer, Player> players;
  private final int numberOfPlayers;

  public Game(int numberOfPlayers) {
    this.board = new Board();
    this.players = new HashMap<>();
    this.numberOfPlayers = numberOfPlayers;
  }

  public void doAction(Action action) throws InvalidActionException {

  }

  public Board getBoard() {
    return board;
  }
}
