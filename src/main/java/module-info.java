module com.shepherdjerred.capstone.logic {
  requires static lombok;

  requires org.apache.logging.log4j;
  requires com.google.common;
  requires ai.algorithms;

  exports com.shepherdjerred.capstone.logic.board;
  exports com.shepherdjerred.capstone.logic.board.exception;
  exports com.shepherdjerred.capstone.logic.piece;
  exports com.shepherdjerred.capstone.logic.player;
  exports com.shepherdjerred.capstone.logic.player.exception;
  exports com.shepherdjerred.capstone.logic.match;
  exports com.shepherdjerred.capstone.logic.turn;
  exports com.shepherdjerred.capstone.logic.turn.notation;
  exports com.shepherdjerred.capstone.logic.turn.validator;
  exports com.shepherdjerred.capstone.logic.turn.enactor;
  exports com.shepherdjerred.capstone.logic.turn.exception;
  exports com.shepherdjerred.capstone.logic.turn.generator;
  exports com.shepherdjerred.capstone.logic.board.search;
  exports com.shepherdjerred.capstone.logic.util;
}
