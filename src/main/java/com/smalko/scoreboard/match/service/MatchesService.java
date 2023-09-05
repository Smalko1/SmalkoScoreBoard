package com.smalko.scoreboard.match.service;

import com.smalko.scoreboard.match.model.dto.MatchesCreateDto;
import com.smalko.scoreboard.match.model.dto.MatchesReadDto;
import com.smalko.scoreboard.match.model.mapper.MatchesCreateMapper;
import com.smalko.scoreboard.match.model.mapper.MatchesReadMapper;
import com.smalko.scoreboard.match.model.repository.MatchesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MatchesService {

    private final MatchesRepository matchesRepository;
    private final MatchesCreateMapper matchesCreateMapper;
    private final MatchesReadMapper matchesReadMapper;

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
