package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A match of Quoridor.
 */
// TODO this could still be cleaned up more
@Getter
@ToString
@EqualsAndHashCode
public class Match {

  private final Board board;
  private final MatchSettings matchSettings;
  private final PlayerWallBank playerWallBank;
  private final MatchStatus matchStatus;
  private final MatchHistory matchHistory;
  private final MatchTurnEnactor matchTurnEnactor;
  private final ActivePlayerTracker activePlayerTracker;

  public static Match from(MatchSettings matchSettings, BoardSettings boardSettings) {
    var board = Board.from(boardSettings);
    return from(matchSettings, board);
  }

  // TODO board validation
  public static Match from(MatchSettings matchSettings, Board board) {
    var playerCount = matchSettings.getPlayerCount();
    var startingPlayer = matchSettings.getStartingPlayerId();
    var wallPool = PlayerWallBank.from(matchSettings.getPlayerCount(),
        matchSettings.getWallsPerPlayer());
    var matchStatus = new MatchStatus(PlayerId.NULL, Status.IN_PROGRESS);
    var matchHistory = new MatchHistory();
    var activePlayerTracker = new ActivePlayerTracker(startingPlayer, playerCount);
    var matchTurnEnactor = new MatchTurnEnactor(new TurnEnactorFactory(),
        new TurnValidatorFactory(),
        new MatchStatusUpdater(new PlayerGoals()));

    return new Match(board,
        matchSettings,
        wallPool,
        matchStatus,
        matchHistory,
        matchTurnEnactor,
        activePlayerTracker);
  }

  public Match(Board board,
      MatchSettings matchSettings,
      PlayerWallBank playerWallBank,
      MatchStatus matchStatus,
      MatchHistory matchHistory,
      MatchTurnEnactor matchTurnEnactor,
      ActivePlayerTracker activePlayerTracker) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayerTracker = activePlayerTracker;
    this.playerWallBank = playerWallBank;
    this.matchStatus = matchStatus;
    this.matchHistory = matchHistory;
    this.matchTurnEnactor = matchTurnEnactor;
  }

  public Match doTurn(Turn turn) {
    return matchTurnEnactor.enactTurn(turn, this);
  }

  public int getWallsLeft(PlayerId playerId) {
    return playerWallBank.getWallsLeft(playerId);
  }

  public PlayerId getActivePlayerId() {
    return activePlayerTracker.getActivePlayer();
  }

  public PlayerId getNextActivePlayerId() {
    return activePlayerTracker.getNextActivePlayerId();
  }
}
