package com.smalko.scoreboard.player.service;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.mapper.PlayerCreateMapper;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerCreateMapper playerCreateMapper;
    private final PlayerReadMapper playerReadMapper;


    @Transactional
    public Integer createPlayer(PlayersCreateDto playerDto) {
        var playersEntity = playerCreateMapper.mapFrom(playerDto);
        return playerRepository.save(playersEntity).getId();
    }

    @Transactional
    public Optional<PlayerReadDto> getPlayersForId(Integer id) {
        return playerRepository.findById(id)
                .map(playerReadMapper::mapFrom);
    }

    public Optional<PlayerReadDto> getPlayersForName(String name){
return null;
    }


}

