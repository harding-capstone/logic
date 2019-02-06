module com.shepherdjererd.capstone.logic {
  requires static lombok;
  requires com.google.common;

  exports com.shepherdjerred.capstone.logic;
  exports com.shepherdjerred.capstone.logic.board;
  exports com.shepherdjerred.capstone.logic.board.pieces;
  exports com.shepherdjerred.capstone.logic.board.exception;
  exports com.shepherdjerred.capstone.logic.board.layout;
  exports com.shepherdjerred.capstone.logic.piece;
  exports com.shepherdjerred.capstone.logic.match;
  exports com.shepherdjerred.capstone.logic.turn;
  exports com.shepherdjerred.capstone.logic.turn.validator;
  exports com.shepherdjerred.capstone.logic.turn.exception;
}
