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
@Table(name="matches")
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player_one_id", nullable = false)
    private Players playerOneId;
    @ManyToOne
    @JoinColumn(name = "player_two_id", nullable = false)
    private Players playerTwoId;

    @ManyToOne
    @JoinColumn(name = "winner" ,nullable = false)
    private Players winner;
}
