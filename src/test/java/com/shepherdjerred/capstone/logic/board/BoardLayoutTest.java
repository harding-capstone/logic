package com.shepherdjerred.capstone.logic.board;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.shepherdjerred.capstone.logic.board.layout.BoardLayout;
import com.shepherdjerred.capstone.logic.board.layout.BoardLayoutInitializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BoardLayoutTest {

  @Mock
  private BoardSettings boardSettings;
  @Mock
  private BoardLayoutInitializer initializer;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void from_CallsInitializer() {
    BoardLayout.from(boardSettings, initializer);
    verify(initializer, times(1)).createBoardCells(boardSettings);
  }
}
