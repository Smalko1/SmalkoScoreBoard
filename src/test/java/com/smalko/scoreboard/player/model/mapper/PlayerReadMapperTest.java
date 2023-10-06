package com.smalko.scoreboard.player.model.mapper;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.entity.Players;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerReadMapperTest {
    private final PlayerReadMapper mapper = PlayerReadMapper.getInstance();

    @Test
    void mapFrom() {
        var player = Players.builder()
                .id(1)
                .name("Smalko")
                .build();

        var actualResul = mapper.mapFrom(player);

        var expendResult = new PlayerReadDto(1, "Smalko");

        Assertions.assertThat(actualResul).isEqualTo(expendResult);
    }
}