package com.smalko.scoreboard.player.model.mapper;

import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.entity.Players;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerCreateMapperTest {
    private final PlayerCreateMapper mapper = PlayerCreateMapper.getInstance();
    @Test
    void mapFrom() {
        var createDto = new PlayersCreateDto("Smalko");

        var actualResult = mapper.mapFrom(createDto);

        var expendResult = Players.builder()
                .name("Smalko")
                .build();

        assertThat(actualResult).isEqualTo(expendResult);
    }
}