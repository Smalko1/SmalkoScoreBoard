package com.smalko.scoreboard.match.model.mapper;

import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.util.Mapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchesReadMapper implements Mapper<Matches, MatchesReadDto> {
    private final PlayerReadMapper playerReadMapper = PlayerReadMapper.getInstance();
    private static final MatchesReadMapper INSTANCE = new MatchesReadMapper();

    private static final String UNICODE_CUP = " \uD83C\uDFC6";

    @Override
    public MatchesReadDto mapFrom(Matches object) {
        var winner = object.getWinner();
        var playersOneId = object.getPlayersOneId();
        var playersTwoId = object.getPlayersTwoId();
        if (playersOneId.equals(winner)) {
            playersOneId.setName(playersOneId.getName() + UNICODE_CUP);
        } else
            playersTwoId.setName(playersTwoId.getName() + UNICODE_CUP);

        return new MatchesReadDto(
                object.getId(),
                playerReadMapper.mapFrom(playersOneId),
                playerReadMapper.mapFrom(playersTwoId)
        );
    }

    public static MatchesReadMapper getInstance() {
        return INSTANCE;
    }
}
