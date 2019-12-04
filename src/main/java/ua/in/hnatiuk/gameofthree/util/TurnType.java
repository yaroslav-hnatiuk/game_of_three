package ua.in.hnatiuk.gameofthree.util;

public enum TurnType {
    MINUS_ONE(-1), ZERO(0), PLUS_ONE(1);

    public int value;

    TurnType(int value) {
        this.value = value;
    }
}
