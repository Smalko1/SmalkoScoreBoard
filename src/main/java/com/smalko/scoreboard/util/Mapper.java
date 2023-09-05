package com.smalko.scoreboard.util;

public interface Mapper <F, T>{

    T mapFrom(F object);
}
