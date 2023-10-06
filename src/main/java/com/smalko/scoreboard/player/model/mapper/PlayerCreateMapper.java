package com.smalko.scoreboard.player.model.mapper;

import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.Mapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PlayerCreateMapper implements Mapper<PlayersCreateDto, Players> {
    private static final PlayerCreateMapper INSTANCE = new PlayerCreateMapper();
    @Override
    public Players mapFrom(PlayersCreateDto object) {
        return Players.builder()
                .name(object.name())
                .build();
    }

    public static PlayerCreateMapper getInstance() {
        return INSTANCE;
    }
}
