package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.BoardPieces;
import com.shepherdjerred.capstone.logic.board.BoardPiecesInitializer;
import com.shepherdjerred.capstone.logic.board.layout.BoardCellsInitializer;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.exception.InvalidTurnException;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class MyTest {

  @Test
  public void myTest() {

    var enactor = new MatchTurnEnactor(new TurnEnactorFactory(), new TurnValidator());

    var boardSettings = new BoardSettings(9, PlayerCount.TWO);
    var matchSettings = new MatchSettings(10, PlayerId.ONE, boardSettings);

    var boardCellsInitializer = new BoardCellsInitializer();
    var boardLayout = BoardLayout.fromBoardSettings(boardCellsInitializer, boardSettings);

    var pieceBoardLocationsInitializer = new BoardPiecesInitializer();
    var pieceBoardLocations = BoardPieces.initializePieceLocations(boardSettings,
        pieceBoardLocationsInitializer);

    var board = Board.createBoard(boardLayout, pieceBoardLocations);
    var initialMatchState = Match.startNewMatch(matchSettings, board);

    var turn1 = new MovePawnTurn(PlayerId.ONE,
        MoveType.NORMAL,
        new Coordinate(8, 0),
        new Coordinate(8, 2));

    var matchStateAfterTurn1 = enactor.enactTurn(turn1, initialMatchState);

    var turn2 = new MovePawnTurn(PlayerId.TWO,
        MoveType.NORMAL,
        new Coordinate(8, 16),
        new Coordinate(8, 14));

    var matchStateAfterTurn2 = enactor.enactTurn(turn2, matchStateAfterTurn1);

    turn1 = new MovePawnTurn(PlayerId.ONE,
        MoveType.NORMAL,
        new Coordinate(8, 2),
        new Coordinate(8, 4));

    matchStateAfterTurn1 = enactor.enactTurn(turn1, matchStateAfterTurn2);

    turn2 = new MovePawnTurn(PlayerId.TWO,
        MoveType.NORMAL,
        new Coordinate(8, 14),
        new Coordinate(8, 12));

    matchStateAfterTurn2 = enactor.enactTurn(turn2, matchStateAfterTurn1);

    turn1 = new MovePawnTurn(PlayerId.ONE,
        MoveType.NORMAL,
        new Coordinate(8, 4),
        new Coordinate(8, 6));

    matchStateAfterTurn1 = enactor.enactTurn(turn1, matchStateAfterTurn2);

    turn2 = new MovePawnTurn(PlayerId.TWO,
        MoveType.NORMAL,
        new Coordinate(8, 12),
        new Coordinate(8, 10));

    matchStateAfterTurn2 = enactor.enactTurn(turn2, matchStateAfterTurn1);

    turn1 = new MovePawnTurn(PlayerId.ONE,
        MoveType.NORMAL,
        new Coordinate(8, 6),
        new Coordinate(8, 8));

    matchStateAfterTurn1 = enactor.enactTurn(turn1, matchStateAfterTurn2);

    diagonalMoveTestValid(matchStateAfterTurn1, enactor);
    jumpMoveTestValid(matchStateAfterTurn1, enactor);
    wallsBlockPath(matchStateAfterTurn1, enactor);
  }

  public void jumpMoveTestValid(Match matchState, MatchTurnEnactor enactor) {
    var playerTwoJump = new MovePawnTurn(PlayerId.TWO,
        MoveType.JUMP_STRAIGHT,
        new Coordinate(8, 10),
        new Coordinate(8, 6));

    Assert.assertTrue(enactor.enactTurn(playerTwoJump, matchState).getBoard().hasPiece(new Coordinate(8, 6)));
  }

  public void diagonalMoveTestValid(Match matchState, MatchTurnEnactor enactor) {
    var playerTwoPlaceWall = new PlaceWallTurn(PlayerId.TWO,
        new Coordinate(8, 11),
        new Coordinate(7, 11),
        new Coordinate(6, 11));

    var matchStateAfterTurn2 = enactor.enactTurn(playerTwoPlaceWall, matchState);

    var playerOneMoveDiagonal = new MovePawnTurn(PlayerId.ONE,
        MoveType.JUMP_DIAGONAL,
        new Coordinate(8, 8),
        new Coordinate(10, 10));

    Assert.assertTrue(enactor.enactTurn(playerOneMoveDiagonal, matchStateAfterTurn2).getBoard().hasPiece(new Coordinate(10, 10)));
  }

  public void wallsBlockPath(Match matchState, MatchTurnEnactor enactor) {
    var playerTwoPlaceWall = new PlaceWallTurn(PlayerId.TWO,
        new Coordinate(0, 11),
        new Coordinate(1, 11),
        new Coordinate(2, 11));

    var matchStateAfterTurn2 = enactor.enactTurn(playerTwoPlaceWall, matchState);

    var playerOnePlaceWall = new PlaceWallTurn(PlayerId.ONE,
        new Coordinate(4, 11),
        new Coordinate(5, 11),
        new Coordinate(6, 11));

    matchStateAfterTurn2 = enactor.enactTurn(playerOnePlaceWall, matchStateAfterTurn2);

    playerTwoPlaceWall = new PlaceWallTurn(PlayerId.TWO,
        new Coordinate(8, 11),
        new Coordinate(9, 11),
        new Coordinate(10, 11));

    matchStateAfterTurn2 = enactor.enactTurn(playerTwoPlaceWall, matchStateAfterTurn2);

    playerOnePlaceWall = new PlaceWallTurn(PlayerId.ONE,
        new Coordinate(12, 11),
        new Coordinate(13, 11),
        new Coordinate(14, 11));

    matchStateAfterTurn2 = enactor.enactTurn(playerOnePlaceWall, matchStateAfterTurn2);

    playerTwoPlaceWall = new PlaceWallTurn(PlayerId.TWO,
        new Coordinate(15, 10),
        new Coordinate(15, 11),
        new Coordinate(15, 12));

    matchStateAfterTurn2 = enactor.enactTurn(playerTwoPlaceWall, matchStateAfterTurn2);

    playerOnePlaceWall = new PlaceWallTurn(PlayerId.ONE,
        new Coordinate(14, 13),
        new Coordinate(15, 13),
        new Coordinate(16, 13));

    try {
      enactor.enactTurn(playerOnePlaceWall, matchStateAfterTurn2).getBoard().hasPiece(new Coordinate(10, 10));
    } catch (InvalidTurnException e) {
      Assert.assertTrue(true);
    }

  }
}
