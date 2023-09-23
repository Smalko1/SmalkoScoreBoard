package com.smalko.scoreboard.match.model.dto;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;

public record MatchesReadDto(Integer id,
                             PlayerReadDto playersOne,
                             PlayerReadDto playersTwo){
}
