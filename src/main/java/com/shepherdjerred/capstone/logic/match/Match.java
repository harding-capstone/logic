package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
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

  public Match(MatchSettings matchSettings,
      TurnEnactorFactory turnEnactorFactory, TurnValidator turnValidator) {
    this.board = new Board(matchSettings.getBoardSettings(), matchSettings.getPlayerCount());
    this.matchSettings = matchSettings;
    this.activePlayer = matchSettings.getStartingPlayer();
    this.wallPool = initializePlayerWalls(matchSettings);
    this.matchStatus = new MatchStatus(Player.NULL, Status.IN_PROGRESS);
    this.turnEnactorFactory = turnEnactorFactory;
    this.turnValidator = turnValidator;
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

  private MatchStatus updateMatchStatus(Turn turn, Board newBoard) {
    // TODO check for stalemate
    // TODO check for victory
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
  private Map<Player, Integer> initializePlayerWalls(MatchSettings matchSettings) {
    int wallsPerPlayer = matchSettings.getWallsPerPlayer();
    if (matchSettings.getPlayerCount() == PlayerCount.TWO) {
      return initializeWallsForTwoPlayers(wallsPerPlayer);
    } else if (matchSettings.getPlayerCount() == PlayerCount.FOUR) {
      return initializeWallsForFourPlayers(wallsPerPlayer);
    } else {
      throw new IllegalStateException("Unknown player count " + matchSettings.getPlayerCount());
    }
  }

  private Map<Player, Integer> initializeWallsForTwoPlayers(int wallsPerPlayer) {
    HashMap<Player, Integer> playerWallMap = new HashMap<>();
    playerWallMap.put(Player.ONE, wallsPerPlayer);
    playerWallMap.put(Player.TWO, wallsPerPlayer);
    return playerWallMap;
  }

  private Map<Player, Integer> initializeWallsForFourPlayers(
      int wallsPerPlayer) {
    HashMap<Player, Integer> playerWallMap = new HashMap<>();
    playerWallMap.put(Player.ONE, wallsPerPlayer);
    playerWallMap.put(Player.TWO, wallsPerPlayer);
    playerWallMap.put(Player.THREE, wallsPerPlayer);
    playerWallMap.put(Player.FOUR, wallsPerPlayer);
    return playerWallMap;
  }
}
