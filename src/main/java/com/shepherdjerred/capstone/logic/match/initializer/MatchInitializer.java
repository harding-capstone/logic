package com.shepherdjerred.capstone.logic.match.initializer;

import com.google.common.collect.ImmutableMap;
import com.shepherdjerred.capstone.logic.match.MatchSettings;
import com.shepherdjerred.capstone.logic.Player;

public interface MatchInitializer {
  ImmutableMap<Player, Integer> initializePlayerWalls(MatchSettings matchSettings);
}
