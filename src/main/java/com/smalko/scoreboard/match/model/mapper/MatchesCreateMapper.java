package com.smalko.scoreboard.match.model.mapper;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import com.smalko.scoreboard.util.Mapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchesCreateMapper implements Mapper<MatchesCreateDto, Matches> {

    private final PlayerRepository playerRepository;
    @Override
    public Matches mapFrom(MatchesCreateDto object) {
        return Matches.builder()
                .playersOneId(playerRepository.findById(object.playersOneId()).orElseThrow(IllegalArgumentException::new))
                .playersTwoId(playerRepository.findById(object.playersTwoId()).orElseThrow(IllegalArgumentException::new))
                .winner(playerRepository.findById(object.playersTwoId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
