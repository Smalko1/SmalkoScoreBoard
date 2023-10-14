package com.smalko.scoreboard.player.model.mapper;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.Mapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerReadMapper implements Mapper<Players, PlayerReadDto> {

    @Override
    public PlayerReadDto mapFrom(Players object) {
        return new PlayerReadDto(object.getId(), object.getName());
    }

}
