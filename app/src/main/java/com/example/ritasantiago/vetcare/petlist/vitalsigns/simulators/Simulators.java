package com.example.ritasantiago.vetcare.petlist.vitalsigns.simulators;

import java.util.Random;

/**
 * Created by ritasantiago on 21-03-2018.
 */

public class Simulators {
    private static final int TIME = 1; //1 minute of refreshing
    private static final int TEMPERATURE_MIN = 20;
    private static final int TEMPERATURE_MAX = 40;
    private static final int BAT_MIN = 60;
    private static final int BAT_MAX = 100;

    int temperature = calcTemperature(TEMPERATURE_MIN,TEMPERATURE_MAX);
    int bat = calcBat(BAT_MIN, BAT_MAX);

    private static int calcTemperature(int min, int max){
        Random r = new Random();
        int difference = r.nextInt(max-min)+min;
        return difference;
    }

    private static int calcBat(int min, int max){
        Random r = new Random();
        int difference = r.nextInt(max-min)+min;
        return difference;
    }
}
