package com.smalko.scoreboard.match.model.mapper;

import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.util.Mapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchesReadMapper implements Mapper<Matches, MatchesReadDto> {
    private static final String UNICODE_CUP = " \uD83C\uDFC6";

    @Override
    public MatchesReadDto mapFrom(Matches object) {
        var winner = object.getWinner();
        var playersOneId = object.getPlayerOneId();
        var playersTwoId = object.getPlayerTwoId();
        if (playersOneId.equals(winner)) {
            return new MatchesReadDto(
                    object.getId(),
                    playersOneId.getName() +  UNICODE_CUP,
                    playersTwoId.getName()
            );
        } else
            return new MatchesReadDto(
                    object.getId(),
                    playersOneId.getName(),
                    playersTwoId.getName() + UNICODE_CUP
            );
    }
}
