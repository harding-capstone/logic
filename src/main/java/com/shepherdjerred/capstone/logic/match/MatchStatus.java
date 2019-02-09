package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class MatchStatus {

  private final Player victor;
  private final Status status;

  public enum Status {
    IN_PROGRESS, STALEMATE, VICTORY
  }
}
