package com.shepherdjerred.capstone.logic.match;

import static org.junit.Assert.assertTrue;

import com.shepherdjerred.capstone.logic.board.BoardSettings;
import com.shepherdjerred.capstone.logic.player.PlayerCount;
import com.shepherdjerred.capstone.logic.player.QuoridorPlayer;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class ModelTests {
  @Test
  public void MatchConstructor_CreatesValidMatch_WhenJsonStringPassedAsParameter() {
    String match = GetJsonStringForMatch();

    Match returnedMatch = new Match(match);

    assertTrue(returnedMatch.getActivePlayerId() == QuoridorPlayer.ONE);
    assertTrue(returnedMatch.getBoard().getGridSize() == 17);
    assertTrue(returnedMatch.getMatchSettings().getWallsPerPlayer() == 10);
  }

  @Test
  public void ToJson_ConvertsMatchToJson_ValidJson() {
    var match = Match.from(new MatchSettings(10, QuoridorPlayer.ONE, PlayerCount.TWO),
        new BoardSettings(9, PlayerCount.TWO));

    var returnedJson = match.toJson();

    assertTrue(returnedJson.equals(GetJsonStringForMatch()));
  }

  private String GetJsonStringForMatch() {
    String jsonString;

    try {
      var file = new File("src/test/resources/jsonString");
      jsonString = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    } catch (Exception e) {
      jsonString = "File Does Not Exist";
    }

    return jsonString;
  }
}
