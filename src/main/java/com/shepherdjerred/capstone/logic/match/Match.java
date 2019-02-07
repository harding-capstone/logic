package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.exception.InvalidPlayerException;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A match of Quoridor
 */
@ToString
@EqualsAndHashCode
public final class Match {

  @Getter
  private final Board board;
  @Getter
  private final MatchSettings matchSettings;
  @Getter
  private final Player activePlayer;
  private final Map<Player, Integer> wallPool;
  @Getter
  private final MatchStatus matchStatus;
  private final TurnEnactorFactory turnEnactorFactory;
  private final TurnValidator turnValidator;

  public static Match startNewMatch(MatchSettings matchSettings,
      TurnEnactorFactory turnEnactorFactory, TurnValidator turnValidator) {
    var boardSettings = matchSettings.getBoardSettings();
    var boardLayout = BoardLayout.fromBoardSettings(boardSettings);
    var board = Board.createNewBoard(boardLayout, boardSettings, matchSettings.getPlayerCount());
    var startingPlayer = matchSettings.getStartingPlayer();
    var wallPool = initializePlayerWalls(matchSettings);
    var matchStatus = new MatchStatus(Player.NULL, Status.IN_PROGRESS);
    return new Match(board,
        matchSettings,
        startingPlayer,
        wallPool,
        matchStatus,
        turnEnactorFactory,
        turnValidator);
  }

  private Match(Board board,
      MatchSettings matchSettings,
      Player activePlayer,
      Map<Player, Integer> wallPool,
      MatchStatus matchStatus,
      TurnEnactorFactory turnEnactorFactory,
      TurnValidator turnValidator) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayer = activePlayer;
    this.wallPool = wallPool;
    this.matchStatus = matchStatus;
    this.turnEnactorFactory = turnEnactorFactory;
    this.turnValidator = turnValidator;
  }

  public Match doTurn(Turn turn) {
    var validatorResult = turnValidator.isTurnValid(turn, this);
    if (validatorResult.isError()) {
      throw new InvalidTurnException(turn, validatorResult);
    } else {
      return doTurnUnchecked(turn);
    }
  }

  // TODO extract this
  private Match doTurnUnchecked(Turn turn) {
    var enactor = turnEnactorFactory.getEnactor(turn);
    var newBoard = enactor.enactTurn(turn, board);
    var newWallPool = decrementPlayerWalls(turn.getCauser());
    var newMatchStatus = updateMatchStatus(turn, newBoard);
    return new Match(newBoard, matchSettings, getNextActivePlayer(),
        newWallPool, matchStatus, turnEnactorFactory, turnValidator);
  }

  // TODO extract this
  private MatchStatus updateMatchStatus(Turn turn, Board newBoard) {
    // TODO check for stalemate
    // TODO check for victory
    var player = turn.getCauser();
    if (turn instanceof MovePawnTurn) {
      var boardSettings = newBoard.getBoardSettings();
      var gridSize = boardSettings.getGridSize();
      var movePawnTurn = (MovePawnTurn) turn;
      var destination = movePawnTurn.getDestination();

      switch (player) {
        case ONE:
          if (destination.getY() == gridSize - 1) {
            return new MatchStatus(Player.ONE, Status.VICTORY);
          }
          break;
        case TWO:
          if (destination.getY() == 0) {
            return new MatchStatus(Player.TWO, Status.VICTORY);
          }
          break;
        case THREE:
          if (destination.getX() == gridSize - 1) {
            return new MatchStatus(Player.THREE, Status.VICTORY);
          }
          break;
        case FOUR:
          if (destination.getX() == 0) {
            return new MatchStatus(Player.FOUR, Status.VICTORY);
          }
          break;
        default:
          throw new InvalidPlayerException(player);
      }
    }
    return matchStatus;
  }

  // TODO extract this
  private Map<Player, Integer> decrementPlayerWalls(Player player) {
    Map<Player, Integer> newRemainingPlayerWalls = new HashMap<>(wallPool);
    var oldValue = newRemainingPlayerWalls.get(player);
    newRemainingPlayerWalls.put(player, oldValue - 1);
    return newRemainingPlayerWalls;
  }

  /**
   * Get the number of walls a player has left
   */
  public int getRemainingWalls(Player player) {
    return wallPool.get(player);
  }

  /**
   * Returns the player who comes after the active player
   */
  public Player getNextActivePlayer() {
    if (matchSettings.getPlayerCount() == PlayerCount.TWO) {
      return getNextActivePlayerForTwoPlayerMatch();
    } else if (matchSettings.getPlayerCount() == PlayerCount.FOUR) {
      return getNextActivePlayerForFourPlayerMatch();
    } else {
      throw new IllegalStateException("Unknown player count " + matchSettings.getPlayerCount());
    }
  }

  private Player getNextActivePlayerForTwoPlayerMatch() {
    switch (activePlayer) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.ONE;
      default:
        throw new IllegalStateException("Current player shouldn't exist " + activePlayer);
    }
  }

  private Player getNextActivePlayerForFourPlayerMatch() {
    switch (activePlayer) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.THREE;
      case THREE:
        return Player.FOUR;
      case FOUR:
        return Player.ONE;
      default:
        throw new IllegalStateException("Current player shouldn't exist " + activePlayer);
    }
  }

  /**
   * Creates a Map with the initial wall count set for each player
   */
  private static Map<Player, Integer> initializePlayerWalls(MatchSettings matchSettings) {
    int wallsPerPlayer = matchSettings.getWallsPerPlayer();
    if (matchSettings.getPlayerCount() == PlayerCount.TWO) {
      return initializeWallsForTwoPlayers(wallsPerPlayer);
    } else if (matchSettings.getPlayerCount() == PlayerCount.FOUR) {
      return initializeWallsForFourPlayers(wallsPerPlayer);
    } else {
      throw new IllegalStateException("Unknown player count " + matchSettings.getPlayerCount());
    }
  }

  private static Map<Player, Integer> initializeWallsForTwoPlayers(int wallsPerPlayer) {
    HashMap<Player, Integer> playerWallMap = new HashMap<>();
    playerWallMap.put(Player.ONE, wallsPerPlayer);
    playerWallMap.put(Player.TWO, wallsPerPlayer);
    return playerWallMap;
  }

  private static Map<Player, Integer> initializeWallsForFourPlayers(
      int wallsPerPlayer) {
    HashMap<Player, Integer> playerWallMap = new HashMap<>();
    playerWallMap.put(Player.ONE, wallsPerPlayer);
    playerWallMap.put(Player.TWO, wallsPerPlayer);
    playerWallMap.put(Player.THREE, wallsPerPlayer);
    playerWallMap.put(Player.FOUR, wallsPerPlayer);
    return playerWallMap;
  }
}
