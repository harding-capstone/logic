package com.shepherdjerred.capstone.logic.board;

import com.shepherdjerred.capstone.logic.Player;
import com.shepherdjerred.capstone.logic.board.BoardSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.board.initializer.BoardInitializer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class Board {

  private final BoardSettings boardSettings;
  private final Map<Player, Coordinate> pawnLocations;
  private final BoardCell[][] boardCells;

  /**
   * Constructor for a new board
   *
   * @param boardSettings Settings for the board
   * @param boardInitializer An initializer to create the BoardCells
   */
  public Board(BoardSettings boardSettings, BoardInitializer boardInitializer) {
    this.boardSettings = boardSettings;
    this.pawnLocations = new HashMap<>();
    this.boardCells = boardInitializer.createBoardCells(boardSettings);
    findPawnLocations();
    validatePawnLocations();
  }

  /**
   * Copy constructor for the Board class
   *
   * @param board The board to copy from
   */
  public Board(Board board) {
    this.boardSettings = board.boardSettings;
    this.pawnLocations = board.pawnLocations;

    var gridSize = board.boardSettings.getGridSize();

    boardCells = new BoardCell[gridSize][gridSize];

    for (int i = 0; i < gridSize; i++) {
      System.arraycopy(board.boardCells[i], 0, boardCells[i], 0, gridSize);
    }
  }

  /**
   * Returns the BoardCell at a given Coordinate
   *
   * @param coordinate Coordinate of the BoardCell to get
   * @return The BoardCell at the Coordinate
   */
  public BoardCell getCell(Coordinate coordinate) {
    return boardCells[coordinate.getX()][coordinate.getY()];
  }

  /**
   * Returns the location of a player's pawn
   *
   * @param player The owner of the pawn to find
   * @return The locations of the pawn
   */
  public Coordinate getPawnLocation(Player player) {
    return pawnLocations.get(player);
  }

  /**
   * Creates a copy of the current board and then updates the cells of the copy with new values
   *
   * @param newCells The cells in the new copy to replace
   * @return A board with its cells update
   */
  public Board updateBoardCells(Map<Coordinate, BoardCell> newCells) {
    Board newBoard = new Board(this);
    newCells.forEach(newBoard::updateCell);
    newBoard.updatePawnLocations(newCells);
    return newBoard;
  }

  /**
   * Updates a single cell of the board.
   *
   * MUTATES THE BOARD. DO NOT CALL EXCEPT ON A COPY OF A BOARD
   *
   * @param coordinate The coordinate to update
   * @param cell The cell to be set at the coordinate
   */
  private void updateCell(Coordinate coordinate, BoardCell cell) {
    var oldCell = getCell(coordinate);
    if (isBoardCellReplacementValid(oldCell, cell)) {
      int x = coordinate.getX();
      int y = coordinate.getY();
      boardCells[x][y] = cell;
    } else {
      throw new IllegalArgumentException("Invalid cell update");
    }
  }

  /**
   * Checks if a cell replacement is valid
   *
   * @param original The original cell
   * @param replacement The replacement cell
   * @return True if the replacement is valid, or false otherwise
   */
  public boolean isBoardCellReplacementValid(BoardCell original, BoardCell replacement) {
    return original.getCellType() == replacement.getCellType();
  }

  /**
   * @return A formatted string representing the board state
   */
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

  /**
   * Finds the location of all pawns on the board and stores them
   *
   * MUTATES THE BOARD
   */
  private void findPawnLocations() {
    var gridSize = boardSettings.getGridSize();

    for (int x = 0; x < gridSize; x++) {
      for (int y = 0; y < gridSize; y++) {
        var coordinate = new Coordinate(x, y);
        var cell = getCell(coordinate);
        if (cell.hasPawn()) {
          var owner = cell.getPiece().getOwner();
          if (pawnLocations.containsKey(owner)) {
            throw new IllegalStateException("A player cannot have two pawns on the board " + cell);
          } else {
            pawnLocations.put(owner, coordinate);
          }
        }
      }
    }
  }

  // TODO Check that the source coordinate had a pawn when moving a pawn

  /**
   * Finds the location of pawns when updating the board
   *
   * MUTATES THE BOARD
   *
   * @param newCells The cells to check
   */
  private void updatePawnLocations(Map<Coordinate, BoardCell> newCells) {
    Map<Player, Coordinate> newPawnLocations = new HashMap<>();

    newCells.forEach(((coordinate, newCell) -> {
      if (newCell.hasPawn()) {
        Player pawnOwner = newCell.getPiece().getOwner();
        if (newPawnLocations.containsKey(pawnOwner)) {
          throw new IllegalStateException(
              "A player cannot have two pawns on the board " + newCell);
        } else {
          newPawnLocations.put(pawnOwner, coordinate);
        }
      }
    }));

    pawnLocations.putAll(newPawnLocations);
    validatePawnLocations();
  }

  /**
   * Gets the player count from the board settings
   *
   * @return A number representing how many players there are on this board
   */
  private int getPlayerCountAsInt() {
    final var playerCount = boardSettings.getPlayerCount();
    if (playerCount == PlayerCount.TWO) {
      return 2;
    } else if (playerCount == PlayerCount.FOUR) {
      return 4;
    } else {
      throw new IllegalStateException("Unknown player count" + playerCount);
    }
  }

  /**
   * Validates that each player has a pawn on the board
   */
  private void validatePawnLocations() {
    final int numberOfPlayers = getPlayerCountAsInt();
    if (pawnLocations.size() != numberOfPlayers) {
      throw new IllegalStateException("Pawn locations does not equal number of players");
    }
    Set<Player> pawnsToFind = new HashSet<>();
    pawnsToFind.add(Player.ONE);
    pawnsToFind.add(Player.TWO);
    if (numberOfPlayers == 4) {
      pawnsToFind.add(Player.THREE);
      pawnsToFind.add(Player.FOUR);
    }

    pawnsToFind.forEach(player -> {
      var pawn = pawnLocations.get(player);
      var cell = getCell(pawn);
      if (!cell.hasPawn() || cell.getPiece().getOwner() != player) {
        throw new IllegalStateException("Invalid pawn state");
      }
    });
  }
}
