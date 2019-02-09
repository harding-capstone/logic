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
@Getter
@ToString
@EqualsAndHashCode
public final class Match {

  private final Board board;
  private final MatchSettings matchSettings;
  private final Player activePlayer;
  private final PlayerWallBank playerWallBank;
  private final MatchStatus matchStatus;
  private final MatchHistory matchHistory;

  // TODO refactor
  // This static factory might result in matches with their invariants violated
  // Maybe there's a better way to do it?
  // Should playerWallBank and matchHistory be extracted?
  public static Match startNewMatch(MatchSettings matchSettings, Board board) {
    if (matchSettings.getBoardSettings() != board.getBoardSettings()) {
      throw new IllegalArgumentException("Board not compatible with match");
    }

    var startingPlayer = matchSettings.getStartingPlayer();
    var wallPool = PlayerWallBank.createWallPool(matchSettings.getBoardSettings().getPlayerCount(),
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
      PlayerWallBank playerWallBank,
      MatchStatus matchStatus,
      MatchHistory matchHistory) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayer = activePlayer;
    this.playerWallBank = playerWallBank;
    this.matchStatus = matchStatus;
    this.matchHistory = matchHistory;
  }

  /**
   * Get the number of walls a player has left
   */
  public int getWallsLeft(Player player) {
    return playerWallBank.getWallsLeft(player);
  }
}
