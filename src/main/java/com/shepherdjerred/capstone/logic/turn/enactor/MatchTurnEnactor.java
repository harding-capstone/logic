package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.match.MatchStatus;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.player.exception.InvalidPlayerException;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;

// TODO this needs a lot of clean up
public class MatchTurnEnactor {

  private final TurnEnactorFactory turnEnactorFactory;
  private final TurnValidator turnValidator;

  public MatchTurnEnactor(TurnEnactorFactory turnEnactorFactory,
      TurnValidator turnValidator) {
    this.turnEnactorFactory = turnEnactorFactory;
    this.turnValidator = turnValidator;
  }

  public Match enactTurn(Turn turn, Match match) {
    var validatorResult = turnValidator.isTurnValid(turn, match);
    if (validatorResult.isError()) {
      throw new InvalidTurnException(turn, validatorResult);
    } else {
      return enactTurnUnchecked(turn, match);
    }
  }

  public Match enactTurnUnchecked(Turn turn, Match match) {
    var enactor = turnEnactorFactory.getEnactor(turn);
    var board = match.getBoard();

    var newBoard = enactor.enactTurn(turn, board);
    var newMatchStatus = updateMatchStatus(turn, match);

    var newWallPool = match.getWallPool();
    if (turn instanceof PlaceWallTurn) {
      newWallPool = newWallPool.takeWall(turn.getCauser());
    }

    var matchHistory = match.getMatchHistory();
    var newHistory = matchHistory.push(match);

    var matchSettings = match.getMatchSettings();

    return new Match(newBoard, matchSettings, getNextActivePlayer(match),
        newWallPool, newMatchStatus, newHistory);
  }


  private MatchStatus updateMatchStatus(Turn turn, Match match) {
    var player = turn.getCauser();
    if (turn instanceof MovePawnTurn) {
      var board = match.getBoard();
      var boardSettings = board.getBoardSettings();
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
    return match.getMatchStatus();
  }

  /**
   * Returns the player who comes after the active player
   */
  public Player getNextActivePlayer(Match match) {
    var activePlayer = match.getActivePlayer();
    var playerCount = match.getMatchSettings().getPlayerCount();

    if (playerCount == PlayerCount.TWO) {
      return getNextActivePlayerForTwoPlayerMatch(activePlayer);
    } else if (playerCount == PlayerCount.FOUR) {
      return getNextActivePlayerForFourPlayerMatch(activePlayer);
    } else {
      throw new IllegalStateException("Unknown player count " + playerCount);
    }
  }

  private Player getNextActivePlayerForTwoPlayerMatch(Player activePlayer) {
    switch (activePlayer) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.ONE;
      default:
        throw new InvalidPlayerException(activePlayer);
    }
  }

  private Player getNextActivePlayerForFourPlayerMatch(Player activePlayer) {
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
        throw new InvalidPlayerException(activePlayer);
    }
  }
}
