package com.smalko.scoreboard.exception;

public class NoSuchUUID extends Exception{
    public NoSuchUUID() {
        super("There is no such UUID");
    }
}
