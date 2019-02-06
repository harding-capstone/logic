package com.shepherdjerred.capstone.logic.turn.enactor;

import com.shepherdjerred.capstone.logic.match.MatchState;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;

public enum MovePawnTurnEnactor implements TurnEnactor {
  INSTANCE;

  /**
   * Takes the steps to transform a given boardState state by the parameters in a turn
   *
   * @param turn The turn to use when transforming the boardState
   * @param matchState The initial matchState state
   * @return The initial matchState state transformed by the turn
   */
  @Override
  public MatchState enactTurn(Turn turn, MatchState matchState) {
    if (turn instanceof MovePawnTurn) {
      return enactMovePawnTurn((MovePawnTurn) turn, matchState);
    } else {
      throw new IllegalArgumentException("Turn is not a MovePawnTurn " + turn);
    }
  }

  // TODO check for victory
  // Also this builder is terrible but ¯\_(ツ)_/¯
  private MatchState enactMovePawnTurn(MovePawnTurn turn, MatchState matchState) {
    var board = matchState.getBoardState();
    var newBoard = board.setPawnPosition(turn.getCauser(), turn.getDestination());
    return MatchState.builder()
        .boardState(newBoard)
        .matchSettings(matchState.getMatchSettings())
        .turnEnactorFactory(matchState.getTurnEnactorFactory())
        .turnValidatorFactory(matchState.getTurnValidatorFactory())
        .currentPlayerTurn(matchState.getNextPlayer())
        .playerWalls(matchState.getPlayerWalls())
        .status(matchState.getStatus())
        .build();
  }
}
