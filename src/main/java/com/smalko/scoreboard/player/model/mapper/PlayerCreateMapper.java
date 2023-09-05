package com.smalko.scoreboard.player.model.mapper;

import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.Mapper;

public class PlayerCreateMapper implements Mapper<PlayersCreateDto, Players> {
    @Override
    public Players mapFrom(PlayersCreateDto object) {
        return Players.builder()
                .name(object.name())
                .build();
    }
}
