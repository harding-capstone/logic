package com.shepherdjerred.capstone.logic.match;

import com.google.common.collect.ImmutableMap;
import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.initializer.DefaultBoardInitializer;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.match.initializer.MatchInitializer;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Match {

  private final Board board;
  private final MatchSettings matchSettings;
  private final TurnValidatorFactory turnValidatorFactory;
  private final TurnEnactorFactory turnEnactorFactory;
  private final Player currentPlayerTurn;
  private final ImmutableMap<Player, Integer> playerWalls;
  private final MatchStatus status;

  public Match(
      MatchSettings matchSettings,
      TurnValidatorFactory TurnValidatorFactory,
      TurnEnactorFactory turnEnactorFactory,
      MatchInitializer matchInitializer
  ) {
    this.board = new Board(matchSettings.getBoardSettings(), DefaultBoardInitializer.INSTANCE);
    this.matchSettings = matchSettings;
    this.turnValidatorFactory = TurnValidatorFactory;
    this.turnEnactorFactory = turnEnactorFactory;
    this.currentPlayerTurn = matchSettings.getStartingPlayer();
    this.playerWalls = matchInitializer.initializePlayerWalls(matchSettings);
    this.status = new MatchStatus(Player.NULL, Status.IN_PROGRESS);
  }

  public int getRemainingWallCount(Player player) {
    return playerWalls.get(player);
  }

  public Match doTurn(Turn turn) throws InvalidTurnException {
    if (currentPlayerTurn == turn.getCauser()) {
      throw new InvalidTurnException(
          "Out of order. Current turn: " + currentPlayerTurn + " Actual: " + turn.getCauser());
    }
    var turnValidator = turnValidatorFactory.getValidator(turn);
    turnValidator.isTurnValid(turn, this);
    return doTurnUnchecked(turn);
  }

  private Match doTurnUnchecked(Turn turn) {
    var turnEnactor = turnEnactorFactory.getEnactor(turn);
    return turnEnactor.enactTurn(turn, this);
  }

  public Player getNextPlayer() {
    if (matchSettings.getPlayerCount() == PlayerCount.TWO) {
      return getNextPlayerForTwoPlayerMatch();
    } else if (matchSettings.getPlayerCount() == PlayerCount.FOUR) {
      return getNextPlayerForFourPlayerMatch();
    } else {
      throw new IllegalStateException("Unknown player count " + matchSettings.getPlayerCount());
    }
  }

  private Player getNextPlayerForTwoPlayerMatch() {
    switch (currentPlayerTurn) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.ONE;
      default:
        throw new IllegalStateException("Current player shouldn't exist " + currentPlayerTurn);
    }
  }

  private Player getNextPlayerForFourPlayerMatch() {
    switch (currentPlayerTurn) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.THREE;
      case THREE:
        return Player.FOUR;
      case FOUR:
        return Player.ONE;
      default:
        throw new IllegalStateException("Current player shouldn't exist " + currentPlayerTurn);
    }
  }
}
