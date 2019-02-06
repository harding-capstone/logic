package com.shepherdjerred.capstone.logic.board.exception;

import com.shepherdjerred.capstone.logic.board.Coordinate;

public class CoordinateOutOfBoundsException extends RuntimeException {

  public CoordinateOutOfBoundsException() {
  }

  public CoordinateOutOfBoundsException(Coordinate coordinate) {
    super(coordinate.toString());
  }

  public CoordinateOutOfBoundsException(String message) {
    super(message);
  }

  public CoordinateOutOfBoundsException(String message, Throwable cause) {
    super(message, cause);
  }

  public CoordinateOutOfBoundsException(Throwable cause) {
    super(cause);
  }

  public CoordinateOutOfBoundsException(String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
