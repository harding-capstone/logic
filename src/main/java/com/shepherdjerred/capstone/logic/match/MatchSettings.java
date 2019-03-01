package com.shepherdjerred.capstone.logic.match;

import com.google.common.base.Preconditions;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class MatchSettings {

  private final int wallsPerPlayer;
  private final PlayerId startingPlayerId;
  private final PlayerCount playerCount;

  public MatchSettings(int wallsPerPlayer,
      PlayerId startingPlayerId,
      PlayerCount playerCount) {
    Preconditions.checkArgument(startingPlayerId.toInt() > playerCount.toInt());
    this.wallsPerPlayer = wallsPerPlayer;
    this.startingPlayerId = startingPlayerId;
    this.playerCount = playerCount;
  }


}
