package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.exception.InvalidPlayerException;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
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
  private final WallPool wallPool;
  @Getter
  private final MatchStatus matchStatus;
  private final TurnEnactorFactory turnEnactorFactory;
  private final TurnValidator turnValidator;
  @Getter
  private final MatchHistory matchHistory;

  // TODO extract this maybe?
  public static Match startNewMatch(MatchSettings matchSettings,
      TurnEnactorFactory turnEnactorFactory, TurnValidator turnValidator) {
    var boardSettings = matchSettings.getBoardSettings();
    var boardLayout = BoardLayout.fromBoardSettings(boardSettings);
    var board = Board.createNewBoard(boardLayout, boardSettings, matchSettings.getPlayerCount());
    var startingPlayer = matchSettings.getStartingPlayer();
    var wallPool = WallPool.createWallPool(matchSettings.getPlayerCount(),
        matchSettings.getWallsPerPlayer());
    var matchStatus = new MatchStatus(Player.NULL, Status.IN_PROGRESS);
    var matchHistory = new MatchHistory();
    return new Match(board,
        matchSettings,
        startingPlayer,
        wallPool,
        matchStatus,
        turnEnactorFactory,
        turnValidator,
        matchHistory);
  }

  private Match(Board board,
      MatchSettings matchSettings,
      Player activePlayer,
      WallPool wallPool,
      MatchStatus matchStatus,
      TurnEnactorFactory turnEnactorFactory,
      TurnValidator turnValidator,
      MatchHistory matchHistory) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayer = activePlayer;
    this.wallPool = wallPool;
    this.matchStatus = matchStatus;
    this.turnEnactorFactory = turnEnactorFactory;
    this.turnValidator = turnValidator;
    this.matchHistory = matchHistory;
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
    var newMatchStatus = updateMatchStatus(turn, newBoard);
    var newWallPool = wallPool;
    if (turn instanceof PlaceWallTurn) {
      newWallPool = wallPool.takeWall(turn.getCauser());
    }
    var newHistory = matchHistory.push(this);
    return new Match(newBoard, matchSettings, getNextActivePlayer(),
        newWallPool, newMatchStatus, turnEnactorFactory, turnValidator, newHistory);
  }

  // TODO extract this
  private MatchStatus updateMatchStatus(Turn turn, Board newBoard) {
    // TODO check for stalemate
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

  /**
   * Get the number of walls a player has left
   */
  public int getWallsLeft(Player player) {
    return wallPool.getWallsLeft(player);
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
