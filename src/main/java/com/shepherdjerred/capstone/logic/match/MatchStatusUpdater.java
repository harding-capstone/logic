package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MatchStatusUpdater {

  private final PlayerGoals playerGoals;

  public MatchStatus updateMatchStatus(Turn turn, Match match) {
    var player = turn.getCauser();
    if (turn instanceof MovePawnTurn) {
      var gridSize = match.getBoard().getBoardSettings().getGridSize();
      var movePawnTurn = (MovePawnTurn) turn;
      var destination = movePawnTurn.getDestination();

      var goals = playerGoals.getGoalCoordinatesForPlayer(player, gridSize);
      if (goals.contains(destination)) {
        return new MatchStatus(player, Status.VICTORY);
      }
    }
    return match.getMatchStatus();
  }
}
