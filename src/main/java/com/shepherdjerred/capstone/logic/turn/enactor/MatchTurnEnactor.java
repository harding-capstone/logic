package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchStatusUpdater;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validators.TurnValidator;
import lombok.AllArgsConstructor;

// TODO this needs to be cleaned up
@AllArgsConstructor
public class MatchTurnEnactor {

  private final TurnEnactorFactory turnEnactorFactory;
  private final TurnValidator turnValidator;
  private final MatchStatusUpdater matchStatusUpdater;

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
    var newMatchStatus = matchStatusUpdater.updateMatchStatus(turn, match);

    // TODO I think this would be better to do in the turn specific handler, but I'm not sure how that can be done well
    var newWallPool = match.getPlayerWallBank();
    if (turn instanceof PlaceWallTurn) {
      newWallPool = newWallPool.takeWall(turn.getCauser());
    }

    var matchHistory = match.getMatchHistory();
    var newHistory = matchHistory.push(match);

    var matchSettings = match.getMatchSettings();

    var newActivePlayerTracker = match.getActivePlayerTracker().getNextActivePlayerTracker();

    return new Match(newBoard, matchSettings,
        newWallPool, newMatchStatus, newHistory, match.getMatchTurnEnactor(), newActivePlayerTracker);
  }
}
