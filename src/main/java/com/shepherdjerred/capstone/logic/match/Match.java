package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
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
  private final PlayerId activePlayerId;
  private final PlayerWallBank playerWallBank;
  private final MatchStatus matchStatus;
  private final MatchHistory matchHistory;
  private final MatchTurnEnactor matchTurnEnactor;

  public static Match from(MatchSettings matchSettings, BoardSettings boardSettings) {
    var board = Board.from(boardSettings);
    return from(matchSettings, board);
  }

  // TODO board validation
  public static Match from(MatchSettings matchSettings, Board board) {
    var startingPlayer = matchSettings.getStartingPlayerId();
    var wallPool = PlayerWallBank.from(matchSettings.getPlayerCount(),
        matchSettings.getWallsPerPlayer());
    var matchStatus = new MatchStatus(PlayerId.NULL, Status.IN_PROGRESS);
    var matchHistory = new MatchHistory();
    var matchTurnEnactor = new MatchTurnEnactor(new TurnEnactorFactory(),
        new TurnValidator(),
        new MatchStatusUpdater(),
        new ActivePlayerTracker(matchSettings.getPlayerCount()));

    return new Match(board,
        matchSettings,
        startingPlayer,
        wallPool,
        matchStatus,
        matchHistory,
        matchTurnEnactor);
  }

  public Match(Board board,
      MatchSettings matchSettings,
      PlayerId activePlayerId,
      PlayerWallBank playerWallBank,
      MatchStatus matchStatus,
      MatchHistory matchHistory,
      MatchTurnEnactor matchTurnEnactor) {
    this.board = board;
    this.matchSettings = matchSettings;
    this.activePlayerId = activePlayerId;
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
}
