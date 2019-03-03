package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ActivePlayerTracker {

  @Getter
  private final PlayerId activePlayer;
  private final PlayerCount playerCount;

  public PlayerId getNextActivePlayerId() {
    return getNextActivePlayerTracker().getActivePlayer();
  }

  public ActivePlayerTracker getNextActivePlayerTracker() {
    var activePlayerInt = activePlayer.toInt() + 1;
    var playerCountInt = playerCount.toInt();

    PlayerId nextPlayer;
    if (activePlayerInt > playerCountInt) {
      nextPlayer = PlayerId.ONE;
    } else {
      nextPlayer = PlayerId.fromInt(activePlayerInt);
    }

    return new ActivePlayerTracker(nextPlayer, playerCount);
  }
}
