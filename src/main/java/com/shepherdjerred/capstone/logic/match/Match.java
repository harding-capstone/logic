package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A match of Quoridor.
 */
@Getter
@ToString
@EqualsAndHashCode
public final class Match {

  private final Board board;
  private final MatchSettings matchSettings;
  private final PlayerId activePlayerId;
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

    var startingPlayer = matchSettings.getStartingPlayerId();
    var wallPool = PlayerWallBank.createWallPool(matchSettings.getBoardSettings().getPlayerCount(),
        matchSettings.getWallsPerPlayer());
    var matchStatus = new MatchStatus(PlayerId.NULL, Status.IN_PROGRESS);
    var matchHistory = new MatchHistory();

    return new Match(board,
        matchSettings,
        startingPlayer,
        wallPool,
        matchStatus,
        matchHistory);
  }

  // I don't like this constructor being public (ideally would be private), but it's the only way we can split up logic for turns
  public Match(Board board,
      MatchSettings matchSettings,
      PlayerId activePlayerId,
      PlayerWallBank playerWallBank,
      MatchStatus matchStatus,
      MatchHistory matchHistory) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayerId = activePlayerId;
    this.playerWallBank = playerWallBank;
    this.matchStatus = matchStatus;
    this.matchHistory = matchHistory;
  }

  public Match doTurn(Turn turn, MatchTurnEnactor enactor) {
    return enactor.enactTurn(turn, this);
  }

  /**
   * Get the number of walls a playerId has left.
   */
  public int getWallsLeft(PlayerId playerId) {
    return playerWallBank.getWallsLeft(playerId);
  }
}
