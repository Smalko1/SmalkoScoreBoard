package com.smalko.scoreboard.match.service;

import com.smalko.scoreboard.interceptor.TransactionInterceptor;
import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.mapper.MatchesReadMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;

import java.util.Optional;

@RequiredArgsConstructor
public class MatchesService {

    private final MatchesRepository matchesRepository;
    private final MatchesCreateMapper matchesCreateMapper;
    private final MatchesReadMapper matchesReadMapper;

    @SneakyThrows
    public static MatchesService openMatchesService(Session session){
        var playerReadMapper = new PlayerReadMapper();
        var playerRepository = new PlayerRepository(session);
        var matchesRepository = new MatchesRepository(session);
        var matchesCreateMapper = new MatchesCreateMapper(playerRepository);
        var matchesReadMapper = new MatchesReadMapper(playerReadMapper);

        var transactionInterceptor = new TransactionInterceptor(session.getSessionFactory());

        return new ByteBuddy()
                .subclass(MatchesService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(MatchesService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(MatchesRepository.class, MatchesCreateMapper.class, MatchesReadMapper.class)
                .newInstance(matchesRepository, matchesCreateMapper, matchesReadMapper);
    }

    @Transactional
    public boolean delete(Integer id){
        var maybeMatches = matchesRepository.findById(id);
        maybeMatches.ifPresent(matches -> matchesRepository.delete(matches.getId()));
        return maybeMatches.isPresent();
    }

    @Transactional
    public Optional<MatchesReadDto> findById(Integer id){
        return  matchesRepository.findById(id)
                .map(matchesReadMapper::mapFrom);
    }

    @Transactional
    public Integer createMatch(MatchesCreateDto matchesCreateDto){

        var matches = matchesCreateMapper.mapFrom(matchesCreateDto);
        return matchesRepository.save(matches).getId();
    }
}
