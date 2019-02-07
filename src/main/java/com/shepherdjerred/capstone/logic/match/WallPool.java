package com.shepherdjerred.capstone.logic.match;

import com.shepherdjerred.capstone.logic.player.Player;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class WallPool {
  public final Map<Player, Integer> playerWalls;
}
