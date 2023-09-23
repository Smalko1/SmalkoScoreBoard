package com.smalko.scoreboard.exception;

public class AbsenceOfThisPlayer extends Exception{
    public AbsenceOfThisPlayer() {
    }

    public AbsenceOfThisPlayer(String message) {
        super(message);
    }
}
