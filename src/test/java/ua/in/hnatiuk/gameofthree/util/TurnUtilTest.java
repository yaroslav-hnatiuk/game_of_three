package ua.in.hnatiuk.gameofthree.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TurnUtilTest {

    @Test
    public void isTurnValidReturnsTrueSuccess(){
        long currentValue = 11;
        TurnType turn = TurnType.PLUS_ONE;
        boolean expectedResult = true;
        boolean result = TurnUtil.isTurnValid(turn, currentValue);

        assertThat(expectedResult, equalTo(result));
    }

    @Test
    public void isTurnValidReturnsFalseSuccess(){
        long currentValue = 10;
        TurnType turn = TurnType.ZERO;
        boolean expectedResult = false;
        boolean result = TurnUtil.isTurnValid(turn, currentValue);

        assertThat(expectedResult, equalTo(result));
    }
}
