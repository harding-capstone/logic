package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.Player;
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
  @Getter
  private final WallPool wallPool;
  @Getter
  private final MatchStatus matchStatus;
  @Getter
  private final MatchHistory matchHistory;

  // TODO refactor
  // This static factory might result in matches with their invariants violated
  //   * Player count might not match, which is very bad
  // Maybe there's a better way to do it?
  // Should wallPool and matchHistory be extracted?
  public static Match startNewMatch(MatchSettings matchSettings, Board board) {
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
        matchHistory);
  }

  public Match(Board board,
      MatchSettings matchSettings,
      Player activePlayer,
      WallPool wallPool,
      MatchStatus matchStatus,
      MatchHistory matchHistory) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayer = activePlayer;
    this.wallPool = wallPool;
    this.matchStatus = matchStatus;
    this.matchHistory = matchHistory;
  }

  /**
   * Get the number of walls a player has left
   */
  public int getWallsLeft(Player player) {
    return wallPool.getWallsLeft(player);
  }
}
