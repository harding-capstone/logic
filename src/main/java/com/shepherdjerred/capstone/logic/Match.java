package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Match {
  private final Board board;
  private final TurnValidatorFactory turnValidatorFactory;
  private final TurnEnactorFactory turnEnactorFactory;

  public Match(Match match) {
    this.board = match.board;
    this.turnValidatorFactory = match.turnValidatorFactory;
    this.turnEnactorFactory = match.turnEnactorFactory;
  }

  public Match doTurn(Turn turn) throws InvalidTurnException {
    var turnValidator = turnValidatorFactory.getValidator(turn);
    turnValidator.isTurnValid(turn, board);
    return doTurnUnchecked(turn);
  }

  private Match doTurnUnchecked(Turn turn) {
    var turnEnactor = turnEnactorFactory.getEnactor(turn);
    var newBoard = turnEnactor.enactTurn(turn, board);
    return new Match(newBoard, turnValidatorFactory, turnEnactorFactory);
  }
}
