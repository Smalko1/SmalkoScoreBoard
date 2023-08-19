package com.smalko.scoreboard.dto;

import com.smalko.scoreboard.entity.Players;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MatchesDto {
    private Integer id;
    private Players playersOne;
    private Players playersTwo;
    private Players winner;

    @Override
    public String toString() {
        return "Matches:{" +
               "id:" + id +
               ", playersOne:" + playersOne.getName() +
               ", playersTwo:" + playersTwo.getName() +
               ", winner:" + winner.getName() +
               '}';
    }
}
