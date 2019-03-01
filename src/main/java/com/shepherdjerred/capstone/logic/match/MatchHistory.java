package com.shepherdjerred.capstone.logic.match;

import java.util.Stack;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class MatchHistory {
  private final Stack<Match> matchHistory;

  public MatchHistory() {
    this.matchHistory = new Stack<>();
  }

  public MatchHistory(Stack<Match> matchHistory) {
    this.matchHistory = matchHistory;
  }

  public MatchHistory push(Match match) {
    Stack<Match> newMatchHistory = new Stack<>();
    newMatchHistory.addAll(matchHistory);
    newMatchHistory.push(match);
    return new MatchHistory(newMatchHistory);
  }

  public Match pop() {
    return matchHistory.pop();
  }

  public boolean isEmpty() {
    return matchHistory.isEmpty();
  }

  public int getSize() {
    return matchHistory.size();
  }
}
