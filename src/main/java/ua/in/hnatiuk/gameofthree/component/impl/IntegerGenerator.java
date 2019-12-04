package ua.in.hnatiuk.gameofthree.component.impl;

import org.springframework.stereotype.Component;
import ua.in.hnatiuk.gameofthree.component.IIntegerGenerator;

import java.util.Random;

@Component
public class IntegerGenerator implements IIntegerGenerator {
    public int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
