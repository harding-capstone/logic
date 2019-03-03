package com.shepherdjerred.capstone.logic.util;

import com.google.common.base.Preconditions;
import com.shepherdjerred.capstone.logic.board.Coordinate;
import com.shepherdjerred.capstone.logic.board.WallPieceLocation;
import com.shepherdjerred.capstone.logic.player.PlayerId;
import com.shepherdjerred.capstone.logic.turn.MovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.NormalMovePawnTurn;
import com.shepherdjerred.capstone.logic.turn.PlaceWallTurn;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NotionTurnFormatter {

  public static String turnToString(Turn turn) {
    if (turn instanceof MovePawnTurn) {
      return movePawnTurnToString((MovePawnTurn) turn);
    } else if (turn instanceof PlaceWallTurn) {
      return placeWallTurnToString((PlaceWallTurn) turn);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private static String movePawnTurnToString(MovePawnTurn turn) {
    var dest = turn.getDestination();
    char yChar = yCoordToNotationChar(dest.getY());
    int xInt = xCoordToNotationInt(dest.getX());
    return String.format("%s%s", yChar, xInt);
  }

  private static String placeWallTurnToString(PlaceWallTurn turn) {
    var location = turn.getLocation();
    var space = getLowerLeftPawnSpace(location);
    var direction = getWallOrientation(location);
    char yChar = yCoordToNotationChar(space.getY());
    int xInt = xCoordToNotationInt(space.getX());
    var directionChar = direction.toNotationChar();
    return String.format("%s%s%s", yChar, xInt, directionChar);
  }

  private static Coordinate getLowerLeftPawnSpace(WallPieceLocation location) {
    return location.getVertex().toLeft().below();
  }

  private static WallOrientation getWallOrientation(WallPieceLocation location) {
    var c1 = location.getFirstCoordinate();
    var c2 = location.getSecondCoordinate();

    if (c1.getX() == c2.getX()) {
      return WallOrientation.VERTICAL;
    }

    if (c1.getY() == c2.getY()) {
      return WallOrientation.HORIZONTAL;
    }

    throw new UnsupportedOperationException();
  }

  private static int xCoordToNotationInt(int xCoord) {
    Preconditions.checkArgument(xCoord % 2 == 0);
    Preconditions.checkArgument(xCoord <= 16);
    return (xCoord / 2) + 1;
  }

  private static char yCoordToNotationChar(int yCoord) {
    Preconditions.checkArgument(yCoord % 2 == 0);
    Preconditions.checkArgument(yCoord <= 16);
    return (char) ((yCoord / 2) + 97);
  }

  private static int notationIntToXCoord(int notationInt) {
    Preconditions.checkArgument(notationInt >= 0);
    Preconditions.checkArgument(notationInt <= 9);
    return (notationInt - 1) * 2;
  }

  private static int notationCharToYCoord(char notationChar) {
    var intValue = (int) notationChar - 97;
    Preconditions.checkArgument(intValue >= 0);
    Preconditions.checkArgument(intValue - 97 <= 9);
    return intValue * 2;
  }

  public static Turn stringToTurn(String string) {
    var length = string.length();
    Preconditions.checkArgument(length == 2 || length == 3);
    string = string.toLowerCase();
    if (length == 2) {
      return stringToMovePawnTurn(string);
    } else {
      return stringToPlaceWallTurn(string);
    }
  }

  private static NormalMovePawnTurn stringToMovePawnTurn(String string) {
    var xCoord = notationIntToXCoord(Character.getNumericValue(string.charAt(1)));
    var yCoord = notationCharToYCoord(string.charAt(0));
    return new NormalMovePawnTurn(PlayerId.NULL, null, new Coordinate(xCoord, yCoord));
  }

  private static PlaceWallTurn stringToPlaceWallTurn(String string) {
    var xCoord = notationIntToXCoord(Character.getNumericValue(string.charAt(1)));
    var yCoord = notationCharToYCoord(string.charAt(0));

    var orientation = WallOrientation.fromNotationChar(string.charAt(2));
    var wallLocation = getWallLocation(new Coordinate(xCoord, yCoord), orientation);
    return new PlaceWallTurn(PlayerId.NULL, wallLocation);
  }

  private static WallPieceLocation getWallLocation(Coordinate coordinate,
      WallOrientation orientation) {
    if (orientation == WallOrientation.HORIZONTAL) {
      var c1 = coordinate.above();
      var vertex = c1.toRight();
      var c2 = vertex.toRight();

      return new WallPieceLocation(c1, vertex, c2);
    } else if (orientation == WallOrientation.VERTICAL) {
      var c1 = coordinate.toRight();
      var vertex = c1.above();
      var c2 = vertex.above();

      return new WallPieceLocation(c1, vertex, c2);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  enum WallOrientation {
    VERTICAL, HORIZONTAL;

    static WallOrientation fromNotationChar(char c) {
      if (c == 'v') {
        return VERTICAL;
      } else if (c == 'h') {
        return HORIZONTAL;
      } else {
        throw new UnsupportedOperationException();
      }
    }

    char toNotationChar() {
      if (this == VERTICAL) {
        return 'v';
      } else if (this == HORIZONTAL) {
        return 'h';
      } else {
        throw new UnsupportedOperationException();
      }
    }
  }
}
