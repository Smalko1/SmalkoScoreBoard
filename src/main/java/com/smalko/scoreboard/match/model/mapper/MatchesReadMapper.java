package com.smalko.scoreboard.match.model.mapper;

import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.util.Mapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchesReadMapper implements Mapper<Matches, MatchesReadDto> {
    private final PlayerReadMapper playerReadMapper;
    @Override
    public MatchesReadDto mapFrom(Matches object) {

        return  new MatchesReadDto(
                object.getId(),
                playerReadMapper.mapFrom(object.getPlayersOneId()),
                playerReadMapper.mapFrom(object.getPlayersTwoId()),
                playerReadMapper.mapFrom(object.getWinner())
                );
        /*
        return MatchesDto.builder()
                .id(object.getId())
                .playersOne(object.getId())
                .playersTwo(object.getId())
                .winner(object.getId())
                .build();

         */
    }
}
