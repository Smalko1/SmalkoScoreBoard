package com.smalko.scoreboard.player.service;

import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.mapper.PlayerCreateMapper;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerService {
    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private static final PlayerCreateMapper playerCreateMapper = PlayerCreateMapper.getInstance();
    private final PlayerReadMapper playerReadMapper = PlayerReadMapper.getInstance();

    @SneakyThrows
    public static PlayerService openPlayerService(Session session){
        var playerRepository = new PlayerRepository(session);

        var playerService = new PlayerService(playerRepository);
        log.info("Create {}, and its completion", playerService);

        /*var transactionInterceptor = new TransactionInterceptor(session.getSessionFactory());

        return new ByteBuddy()
                .subclass(PlayerService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(PlayerService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(PlayerRepository.class, PlayerCreateMapper.class, PlayerReadMapper.class)
                .newInstance(playerRepository, playerCreateMapper, playerReadMapper);
         */
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

    public Optional<PlayerReadDto> getPlayersForName(String name, Session session){
        log.info("Search players for name: {}", name);
        return playerRepository.findByName(name, session)
                .map(players -> new PlayerReadDto(players.getId(), players.getName()));
    }



}

