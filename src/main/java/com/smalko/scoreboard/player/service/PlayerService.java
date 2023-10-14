package com.smalko.scoreboard.player.service;

import com.smalko.scoreboard.exception.AbsenceOfThisPlayer;
import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.mapper.PlayerCreateMapper;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerService {
    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final PlayerCreateMapper playerCreateMapper;
    private final PlayerReadMapper playerReadMapper;

    @SneakyThrows
    public static PlayerService openPlayerService(EntityManager entityManager) {
        var playerRepository = new PlayerRepository(entityManager);
        var playerCreateMapper = new PlayerCreateMapper();
        var playerReadMapper = new PlayerReadMapper();

        var playerService = new PlayerService(playerRepository, playerCreateMapper, playerReadMapper);
        log.info("Create {}, and its completion", playerService);
        return playerService;
    }


    public Integer createPlayer(PlayersCreateDto playerDto) {
        var playersEntity = playerCreateMapper.mapFrom(playerDto);
        log.info("Mapping {} is the {}", playersEntity, playerDto);
        var save = playerRepository.save(playersEntity).getId();
        log.info("save to the player database");
        return save;
    }

    public Optional<PlayerReadDto> getPlayersForId(Integer id) {
        return playerRepository.findById(id)
                .map(playerReadMapper::mapFrom);
    }

    public PlayerReadDto getPlayersForName(String name, EntityManager entityManager) throws AbsenceOfThisPlayer {
        log.info("Search players for name: {}", name);
        var player = playerRepository.findByName(name, entityManager);
        return new PlayerReadDto(player.getId(), player.getName());
    }
}

