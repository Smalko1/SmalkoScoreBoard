package com.smalko.scoreboard.match.model.dto;

public record MatchesCreateDto(Integer playersOneId,
                               Integer playersTwoId,
                               Integer winner) {
}
