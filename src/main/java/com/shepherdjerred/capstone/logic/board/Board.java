package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.BoardSettings;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidatorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Board {

  private final BoardSettings boardSettings;
  private final BoardCell[][] boardCells;
  private final TurnValidatorFactory turnValidatorFactory;
  private final TurnEnactorFactory turnEnactorFactory;

  public Board(Board board) {
    this.boardSettings = board.boardSettings;
    this.turnValidatorFactory = board.turnValidatorFactory;
    this.turnEnactorFactory = board.turnEnactorFactory;

    var gridSize = board.boardSettings.getGridSize();

    boardCells = new BoardCell[gridSize][gridSize];

    for (int i = 0; i < gridSize; i++) {
      System.arraycopy(board.boardCells[i], 0, boardCells[i], 0, gridSize);
    }
  }

  public BoardCell getCell(Coordinate coordinate) {
    return boardCells[coordinate.getX()][coordinate.getY()];
  }

  public Board updateCells(Set<BoardCell> cellSet) {
    Board newBoard = new Board(this);
    cellSet.forEach(cell -> {
      var coordinate = cell.getCoordinate();
      newBoard.boardCells[coordinate.getX()][coordinate.getY()] = cell;
    });
    return newBoard;
  }

  public BoardCell[][] getBoardCells() {
    var gridSize = boardSettings.getGridSize();
    var boardCellsCopy = new BoardCell[gridSize][gridSize];

    for (int i = 0; i < gridSize; i++) {
      System.arraycopy(boardCells[i], 0, boardCells[i], 0, gridSize);
    }

    return boardCellsCopy;
  }

  public Board doTurn(Turn turn) throws InvalidTurnException {
    var turnValidator = turnValidatorFactory.getValidator(turn);
    turnValidator.isTurnValid(turn, this);
    return doTurnUnchecked(turn);
  }

  public String toFormattedString() {
    var sb = new StringBuilder();
    for (int y = boardSettings.getGridSize() - 1; y >= 0; y--) {
      for (int x = 0; x < boardSettings.getGridSize(); x++) {
        var coordinate = new Coordinate(x, y);
        var cell = getCell(coordinate).toChar();
        if (x == 0) {
          sb.append('\n');
        }
        sb.append(cell);
      }
    }
    return sb.toString();
  }

  private Board doTurnUnchecked(Turn turn) {
    var turnEnactor = turnEnactorFactory.getEnactor(turn);
    return turnEnactor.enactTurn(turn, this);
  }
}
