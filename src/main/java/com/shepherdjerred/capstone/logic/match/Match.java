package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.exception.TurnOutOfOrderException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
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
  private final Map<Player, Integer> remainingPlayerWalls;
  @Getter
  private final MatchStatus matchStatus;
  private final TurnEnactorFactory turnEnactorFactory;
  private final TurnValidatorFactory turnValidatorFactory;

  public Match(MatchSettings matchSettings,
      TurnEnactorFactory turnEnactorFactory,
      TurnValidatorFactory turnValidatorFactory) {
    this.board = new Board(matchSettings.getBoardSettings(), matchSettings.getPlayerCount());
    this.matchSettings = matchSettings;
    this.turnEnactorFactory = turnEnactorFactory;
    this.turnValidatorFactory = turnValidatorFactory;
    this.activePlayer = matchSettings.getStartingPlayer();
    this.remainingPlayerWalls = initializePlayerWalls(matchSettings);
    this.matchStatus = new MatchStatus(Player.NULL, Status.IN_PROGRESS);
  }

  private Match(Board board,
      MatchSettings matchSettings,
      Player activePlayer,
      Map<Player, Integer> remainingPlayerWalls,
      MatchStatus matchStatus,
      TurnEnactorFactory turnEnactorFactory,
      TurnValidatorFactory turnValidatorFactory) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.turnEnactorFactory = turnEnactorFactory;
    this.turnValidatorFactory = turnValidatorFactory;
    this.activePlayer = activePlayer;
    this.remainingPlayerWalls = remainingPlayerWalls;
    this.matchStatus = matchStatus;
  }

  /**
   * Do a turn
   */
  public Match doTurn(Turn turn) {
    if (activePlayer != turn.getCauser()) {
      throw new TurnOutOfOrderException(turn);
    }
    var turnValidator = turnValidatorFactory.getValidator(turn);
    if (turnValidator.isTurnValid(turn, this)) {
      var turnEnactor = turnEnactorFactory.getEnactor(turn);
      return turnEnactor.enactTurn(turn, this);
    } else {
      throw new InvalidTurnException(turn);
    }
  }

  /**
   * Get the number of walls a player has left
   */
  public int getRemainingWallCount(Player player) {
    return remainingPlayerWalls.get(player);
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
