package com.smalko.scoreboard.player.model.mapper;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.Mapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PlayerReadMapper implements Mapper<Players, PlayerReadDto> {
    private static final PlayerReadMapper INSTANCE = new PlayerReadMapper();

    @Override
    public PlayerReadDto mapFrom(Players object) {
        return new PlayerReadDto(object.getId(), object.getName());
    }

    public static PlayerReadMapper getInstance(){
        return INSTANCE;
    };
}
