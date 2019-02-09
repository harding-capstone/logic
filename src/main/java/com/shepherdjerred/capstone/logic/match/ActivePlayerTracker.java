package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.match.MatchSettings.PlayerCount;
import com.shepherdjerred.capstone.logic.player.Player;
import com.shepherdjerred.capstone.logic.player.exception.InvalidPlayerException;

// TODO this can be improved
public class ActivePlayerTracker {
  /**
   * Returns the player who comes after the active player
   */
  public Player getNextActivePlayer(Match match) {
    var activePlayer = match.getActivePlayer();
    var playerCount = match.getMatchSettings().getBoardSettings().getPlayerCount();

    if (playerCount == PlayerCount.TWO) {
      return getNextActivePlayerForTwoPlayerMatch(activePlayer);
    } else if (playerCount == PlayerCount.FOUR) {
      return getNextActivePlayerForFourPlayerMatch(activePlayer);
    } else {
      throw new IllegalStateException("Unknown player count " + playerCount);
    }
  }

  private Player getNextActivePlayerForTwoPlayerMatch(Player activePlayer) {
    switch (activePlayer) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.ONE;
      default:
        throw new InvalidPlayerException(activePlayer);
    }
  }

  private Player getNextActivePlayerForFourPlayerMatch(Player activePlayer) {
    switch (activePlayer) {
      case ONE:
        return Player.TWO;
      case TWO:
        return Player.THREE;
      case THREE:
        return Player.FOUR;
      case FOUR:
        return Player.ONE;
      default:
        throw new InvalidPlayerException(activePlayer);
    }
  }
}
