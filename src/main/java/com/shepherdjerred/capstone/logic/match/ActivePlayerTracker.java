package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ActivePlayerTracker {

  private final PlayerCount playerCount;

  public PlayerId nextActivePlayer(PlayerId activePlayer) {
    var activePlayerInt = activePlayer.toInt() + 1;
    var playerCountInt = playerCount.toInt();

    PlayerId nextPlayer;
    if (activePlayerInt > playerCountInt) {
      nextPlayer = PlayerId.ONE;
    } else {
      nextPlayer = PlayerId.fromInt(activePlayerInt);
    }

    return nextPlayer;
  }
}
