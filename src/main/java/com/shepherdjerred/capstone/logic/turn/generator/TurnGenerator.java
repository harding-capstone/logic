package com.shepherdjerred.capstone.logic.turn.generator;

import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.HashSet;
import java.util.Set;

public class TurnGenerator {

  public Set<Turn> generateTurns(Match match) {
    Set<Turn> turns = new HashSet<>();
    var movePawnTurns = generateMovePawnTurns(match);
    var placeWallTurns = generatePlaceWallTurns(match);
    turns.addAll(movePawnTurns);
    turns.addAll(placeWallTurns);
    return turns;
  }

  // TODO brute forcing might not be the best way to do this
  private Set<Turn> generateMovePawnTurns(Match match) {
    Set<Turn> turns = new HashSet<>();

    // TODO try normal move in each direction
    // TODO try straight jumps in each direction
    // TODO try diagonal jumps in each direction

    return turns;
  }

  // TODO ignore moves that won't help us at normal (i.e. placing walls with both coordinates in a different set than the pawns)
  private Set<Turn> generatePlaceWallTurns(Match match) {
    Set<Turn> turns = new HashSet<>();

    // TODO try every possible unique combination

    return turns;
  }
}
