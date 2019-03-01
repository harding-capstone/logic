package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.player.exception.InvalidPlayerException;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public final class MatchStatusUpdater {

  public MatchStatus updateMatchStatus(Turn turn, Match match) {
    var player = turn.getCauser();
    if (turn instanceof MovePawnTurn) {
      var gridSize = match.getBoard().getBoardSettings().getGridSize();
      var movePawnTurn = (MovePawnTurn) turn;
      var destination = movePawnTurn.getDestination();

      switch (player) {
        case ONE:
          if (destination.getY() == gridSize - 1) {
            return new MatchStatus(PlayerId.ONE, Status.VICTORY);
          }
          break;
        case TWO:
          if (destination.getY() == 0) {
            return new MatchStatus(PlayerId.TWO, Status.VICTORY);
          }
          break;
        case THREE:
          if (destination.getX() == gridSize - 1) {
            return new MatchStatus(PlayerId.THREE, Status.VICTORY);
          }
          break;
        case FOUR:
          if (destination.getX() == 0) {
            return new MatchStatus(PlayerId.FOUR, Status.VICTORY);
          }
          break;
        default:
          throw new InvalidPlayerException(player);
      }
    }
    return match.getMatchStatus();
  }
}
