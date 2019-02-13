package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.player.exception.InvalidPlayerException;

// TODO this can be improved
public final class ActivePlayerTracker {
  /**
   * Returns the player who comes after the active player
   */
  public PlayerId getNextActivePlayer(PlayerId activePlayerId, PlayerCount playerCount) {
    if (playerCount == PlayerCount.TWO) {
      return getNextActivePlayerForTwoPlayerMatch(activePlayerId);
    } else if (playerCount == PlayerCount.FOUR) {
      return getNextActivePlayerForFourPlayerMatch(activePlayerId);
    } else {
      throw new IllegalStateException("Unknown player count " + playerCount);
    }
  }

  private PlayerId getNextActivePlayerForTwoPlayerMatch(PlayerId activePlayerId) {
    switch (activePlayerId) {
      case ONE:
        return PlayerId.TWO;
      case TWO:
        return PlayerId.ONE;
      default:
        throw new InvalidPlayerException(activePlayerId);
    }
  }

  private PlayerId getNextActivePlayerForFourPlayerMatch(PlayerId activePlayerId) {
    switch (activePlayerId) {
      case ONE:
        return PlayerId.TWO;
      case TWO:
        return PlayerId.THREE;
      case THREE:
        return PlayerId.FOUR;
      case FOUR:
        return PlayerId.ONE;
      default:
        throw new InvalidPlayerException(activePlayerId);
    }
  }
}
