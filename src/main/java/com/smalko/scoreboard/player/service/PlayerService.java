package com.smalko.scoreboard.player.service;

import com.smalko.scoreboard.interceptor.TransactionInterceptor;
import com.smalko.scoreboard.player.model.dto.PlayerReadDto;
import com.smalko.scoreboard.player.model.dto.PlayersCreateDto;
import com.smalko.scoreboard.player.model.mapper.PlayerCreateMapper;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import com.smalko.scoreboard.util.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerCreateMapper playerCreateMapper;
    private final PlayerReadMapper playerReadMapper;

    @SneakyThrows
    public static PlayerService openPlayerService(){
        var session = HibernateUtil.getSession();

        var playerReadMapper = new PlayerReadMapper();
        var playerCreateMapper = new PlayerCreateMapper();
        var playerRepository = new PlayerRepository(session);

        var transactionInterceptor = new TransactionInterceptor(HibernateUtil.sessionFactory());

        return new ByteBuddy()
                .subclass(PlayerService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(PlayerService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(PlayerRepository.class, PlayerCreateMapper.class, PlayerReadMapper.class)
                .newInstance(playerRepository, playerCreateMapper, playerReadMapper);
    }


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

