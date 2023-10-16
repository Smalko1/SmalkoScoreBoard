package com.smalko.scoreboard.match.service;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.mapper.MatchesReadMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import com.smalko.scoreboard.player.model.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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

    public static MatchesService openMatchesService(EntityManager entityManager){
        var playerRepository = new PlayerRepository(entityManager);
        var matchesRepository = new MatchesRepository(Matches.class, entityManager);
        var matchesCreateMapper = new MatchesCreateMapper(playerRepository);
        var matchesReadMapper = new MatchesReadMapper();

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
        var saveMatch = matchesRepository.save(matches);
        log.info("Create match {}", saveMatch);
    }

    public List<MatchesReadDto> findMatchesInPage(int page){
        int offset = page * 5 - 5;
        int limit = 5;
        log.info("limit {} and offset {} calculation", limit, offset);
        var matches = matchesRepository.findMatchesInPage(offset, limit)
                .stream()
                .map(matchesReadMapper::mapFrom)
                .toList();
        log.info("match extraction in the DB");
        return matches;
    }

    public List<MatchesReadDto> getMatchesForPlayersId(int playerId) {
        var matchesForPlayersId = matchesRepository.findMatchesForPlayersId(playerId)
                .stream()
                .map(matchesReadMapper::mapFrom)
                .toList();
        log.info("Search matches for player id {}", matchesForPlayersId);
        return matchesForPlayersId;
    }

    public long getCountMatch() {
        return matchesRepository.getCountMatches();
    }
}
