package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class MatchStatus {

  private final PlayerId victor;
  private final Status status;

  public enum Status {
    IN_PROGRESS, STALEMATE, VICTORY
  }
}
