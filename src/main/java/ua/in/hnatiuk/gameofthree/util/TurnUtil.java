package ua.in.hnatiuk.gameofthree.util;

public class TurnUtil {
    public static boolean isTurnValid(TurnType turn, long currentNumberValue){
        if ((currentNumberValue + turn.value)%3 == 0) {
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    public static long makeTurn(long currentValue){
        if (isTurnValid(TurnType.ZERO, currentValue)){

            return currentValue / 3;
        } else if(isTurnValid(TurnType.MINUS_ONE, currentValue)){

            return (currentValue + TurnType.MINUS_ONE.value) / 3;
        } else {

            return (currentValue + TurnType.PLUS_ONE.value) / 3;
        }
    }
}
