package com.smalko.scoreboard.controller.score;

import lombok.Getter;

@Getter
public enum Points {
    ZERO_0(0),
    FIFTEEN_15(15),
    THIRTY_30(30),
    FORTY_40(40),
    ADVANTAGE_AD(-1);

    private final int numericValue;

    Points(int numericValue){
        this.numericValue = numericValue;
    }

    public String getNumericValue() {
        if (numericValue == -1){
            return "AD";
        }else
            return String.valueOf(numericValue);
    }
}
