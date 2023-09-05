package com.smalko.scoreboard.match.model.entity;

import com.smalko.scoreboard.player.model.entity.Players;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(unique = true, nullable = false)
    private Players playersOneId;
    @ManyToOne
    @JoinColumn(unique = true, nullable = false)
    private Players playersTwoId;

    @ManyToOne
    @JoinColumn(unique = true, nullable = false)
    private Players winner;
}
