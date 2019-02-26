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
import com.shepherdjerred.capstone.logic.turn.enactor.MatchTurnEnactor;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import com.shepherdjerred.capstone.logic.turn.generator.TurnGenerator;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import java.util.ArrayList;
import java.util.List;
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
    var turn3 = new PlaceWallTurn(PlayerId.ONE,
        new Coordinate(8, 13),
        new Coordinate(6, 13));
//      var turn4 = new MovePawnTurn(PlayerId.TWO,
//          MoveType.NORMAL,
//          new Coordinate(8, 14),
//          new Coordinate(8, 12));

    var matchStateAfterTurn2 = enactor.enactTurn(turn2, matchStateAfterTurn1);
    var matchStateAfterTurn3 = enactor.enactTurn(turn3, matchStateAfterTurn2);
//    var matchStateAfterTurn4 = matchStateAfterTurn3.doTurn(turn4);

    var matchFormatter = new MatchFormatter();

    List<Match> matchStates = new ArrayList<>();
    matchStates.add(initialMatchState);
    matchStates.add(matchStateAfterTurn1);
    matchStates.add(matchStateAfterTurn2);
    matchStates.add(matchStateAfterTurn3);
//    matchStates.add(matchStateAfterTurn4);

    System.out.println(matchFormatter.matchesToString(matchStates));

    var generator = new TurnGenerator();
    System.out.println(generator.generateInvalidTurns(initialMatchState));
    System.out.println(generator.generateInvalidTurns(matchStateAfterTurn1));
    System.out.println(generator.generateInvalidTurns(matchStateAfterTurn2));
    System.out.println(generator.generateInvalidTurns(matchStateAfterTurn3));
  }
}
