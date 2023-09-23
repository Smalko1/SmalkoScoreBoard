package com.smalko.scoreboard.match.service;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.mapper.MatchesReadMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import com.smalko.scoreboard.player.model.mapper.PlayerReadMapper;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MatchesService {
    private static final Logger log = LoggerFactory.getLogger(MatchesService.class);
    private final MatchesRepository matchesRepository;
    private final MatchesCreateMapper matchesCreateMapper;
    private final MatchesReadMapper matchesReadMapper;

    public static MatchesService openMatchesService(Session session){
        var playerReadMapper = new PlayerReadMapper();
        var playerRepository = new PlayerRepository(session);
        var matchesRepository = new MatchesRepository(session);
        var matchesCreateMapper = new MatchesCreateMapper(playerRepository);
        var matchesReadMapper = new MatchesReadMapper(playerReadMapper);

        /*var transactionInterceptor = new TransactionInterceptor(session.getSessionFactory());

        return new ByteBuddy()
                .subclass(MatchesService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(MatchesService.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(MatchesRepository.class, MatchesCreateMapper.class, MatchesReadMapper.class)
                .newInstance(matchesRepository, matchesCreateMapper, matchesReadMapper);
         */
        var matchesService = new MatchesService(matchesRepository, matchesCreateMapper, matchesReadMapper);
        log.info("Create {}, and its completion", matchesService);
        return matchesService;
    }

    public boolean delete(Integer id){
        var maybeMatches = matchesRepository.findById(id);
        maybeMatches.ifPresent(matches -> matchesRepository.delete(matches.getId()));
        return maybeMatches.isPresent();
    }

    public Optional<MatchesReadDto> findById(Integer id){
        return  matchesRepository.findById(id)
                .map(matchesReadMapper::mapFrom);
    }

    public void createMatch(MatchesCreateDto matchesCreateDto){
        var matches = matchesCreateMapper.mapFrom(matchesCreateDto);
        log.info("Mapping {} is the {}", matches, matchesCreateDto);
        matchesRepository.save(matches);
        log.info("Create match");
    }

    public List<MatchesReadDto> findMatchesInPage(int page, Session session){
        int offset = page * 5 + 1;
        int limit = offset + 5;
        log.info("limit and offset calculation");
        var matches = matchesRepository.findMatchesInPage(offset, limit, session)
                .stream()
                .map(matchesReadMapper::mapFrom)
                .toList();
        log.info("match extraction in the DB");
        return matches;

    }

    public List<MatchesReadDto> getMatchesForPlayersId(int playerId, Session session) {
        var matchesForPlayersId = matchesRepository.findMatchesForPlayersId(playerId, session)
                .stream()
                .map(matchesReadMapper::mapFrom)
                .toList();
        log.info("Search matches for player id {}", matchesForPlayersId);
        return matchesForPlayersId;
    }
}
