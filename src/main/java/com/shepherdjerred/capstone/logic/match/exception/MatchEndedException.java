package com.shepherdjerred.capstone.logic.match.exception;

import com.shepherdjerred.capstone.logic.match.Match;

public class MatchEndedException extends RuntimeException {

  public MatchEndedException(Match match) {
    super(match.toString());
  }
}
