package com.shepherdjerred.capstone.logic.turn.validator.placewall;

import com.shepherdjerred.capstone.logic.board.search.BoardSearch;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.PlayerGoals;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import com.shepherdjerred.capstone.logic.turn.validator.ValidatorRule;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class WallDoesntBlockPawnsValidatorRule implements ValidatorRule<PlaceWallTurn> {

  private final BoardSearch boardSearch;
  private final PlayerGoals playerGoals;

  @Override
  public TurnValidationResult validate(Match match, PlaceWallTurn turn) {
    var board = match.getBoard();
    var gridSize = board.getBoardSettings().getGridSize();
    var pawnLocations = board.getPawnLocations();

    var isAnyPawnBlocked = pawnLocations.stream().anyMatch(pawnLocation -> {
      var player = match.getBoard().getPiece(pawnLocation).getOwner();
      var goals = playerGoals.getGoalCoordinatesForPlayer(player, gridSize);
      var doesPathExist = boardSearch.hasPathToAnyDestination(board, pawnLocation, goals);
      log.debug("No path to goal for pawn at " + pawnLocation);
      return doesPathExist;
    });

    if (isAnyPawnBlocked) {
      return new TurnValidationResult();
    } else {
      return new TurnValidationResult(ErrorMessage.WALL_BLOCKS_PAWN_PATH);
    }
  }
}
