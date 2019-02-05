package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.Player;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MatchStatus {

  private final Player victor;
  private final Status status;

  enum Status {
    IN_PROGRESS, STALEMATE, VICTORY
  }
}
