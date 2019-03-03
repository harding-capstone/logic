package com.shepherdjerred.capstone.logic.turn.validator;

import static org.junit.Assert.assertEquals;

import com.shepherdjerred.capstone.logic.turn.validator.TurnValidationResult.ErrorMessage;
import org.junit.Assert;
import org.junit.Test;

public class TurnValidatorResultTest {

  @Test
  public void combine_hasIsErrorSetToTrue_whenOneResultHasError() {
    var left = new TurnValidationResult(true);
    var right = new TurnValidationResult(false);

    var actual = TurnValidationResult.combine(left, right);
    var expected = new TurnValidationResult(true);

    assertEquals(actual, expected);
  }

  @Test
  public void combine_hasIsErrorSetToTrue_whenBothResultsHaveErrors() {
    var left = new TurnValidationResult(true);
    var right = new TurnValidationResult(true);

    var actual = TurnValidationResult.combine(left, right);
    var expected = new TurnValidationResult(true);

    assertEquals(actual, expected);
  }

  @Test
  public void combine_hasErrorFromLeft_whenLeftPassesInError() {
    var left = new TurnValidationResult(ErrorMessage.NULL);
    var right = new TurnValidationResult(false);
    var actual = TurnValidationResult.combine(left, right);

    Assert.assertTrue(actual.isError());
    Assert.assertTrue(actual.getErrors().contains(ErrorMessage.NULL));
  }


  @Test
  public void combine_hasErrorFromRight_whenRightPassesInError() {
    var left = new TurnValidationResult(false);
    var right = new TurnValidationResult(ErrorMessage.NULL);
    var actual = TurnValidationResult.combine(left, right);

    Assert.assertTrue(actual.isError());
    Assert.assertTrue(actual.getErrors().contains(ErrorMessage.NULL));
  }
}
