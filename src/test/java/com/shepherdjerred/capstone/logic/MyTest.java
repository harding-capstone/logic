package com.shepherdjerred.capstone.logic;

import com.shepherdjerred.capstone.logic.board.Board;
import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn.MoveType;
import com.shepherdjerred.capstone.logic.turn.validator.TurnValidator;
import com.shepherdjerred.capstone.logic.util.MatchFormatter;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.enactor.TurnEnactorFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class MyTest {

  @Test
  public void myTest() {
    var boardSettings = new BoardSettings(9);
    var matchSettings = new MatchSettings(10,
        boardSettings,
        PlayerCount.TWO,
        Player.ONE);

    var initialMatchState = Match.startNewMatch(matchSettings,
        Board.createNewBoard(BoardLayout.fromBoardSettings(boardSettings),
            boardSettings,
            matchSettings.getPlayerCount()),
        new TurnEnactorFactory(),
        new TurnValidator());

    var turn1 = new MovePawnTurn(Player.ONE,
        MoveType.NORMAL,
        new Coordinate(8, 0),
        new Coordinate(8, 2));
    var turn2 = new MovePawnTurn(Player.TWO,
        MoveType.NORMAL,
        new Coordinate(8, 16),
        new Coordinate(8, 14));
    var turn3 = new PlaceWallTurn(Player.ONE,
        new Coordinate(8, 13),
        new Coordinate(6, 13));
    var turn4 = new MovePawnTurn(Player.TWO,
        MoveType.NORMAL,
        new Coordinate(8, 14),
        new Coordinate(8, 12));

    var matchStateAfterTurn1 = initialMatchState.doTurn(turn1);
    var matchStateAfterTurn2 = matchStateAfterTurn1.doTurn(turn2);
    var matchStateAfterTurn3 = matchStateAfterTurn2.doTurn(turn3);
//    var matchStateAfterTurn4 = matchStateAfterTurn3.doTurn(turn4);

    var matchFormatter = new MatchFormatter();

    List<Match> matchStates = new ArrayList<>();
    matchStates.add(initialMatchState);
    matchStates.add(matchStateAfterTurn1);
    matchStates.add(matchStateAfterTurn2);
    matchStates.add(matchStateAfterTurn3);
//    matchStates.add(matchStateAfterTurn4);

    System.out.println(matchFormatter.matchesToString(matchStates));
  }
}
