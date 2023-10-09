package com.smalko.scoreboard.controller.score;

import lombok.Getter;

@Getter
public enum Points {
    ZERO_0(0),
    FIFTEEN_15(15),
    THIRTY_30(30),
    FORTY_40(40),
    ADVANTAGE_AD(-1),
    ONE_1(1),
    TWO_2(2),
    TREE_3(3),
    FORE_4(4),
    FIVE_5(5),
    SIX_6(6),
    SEVEN_7(7),
    EIGHT_8(8),
    NINE_9(9);

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
