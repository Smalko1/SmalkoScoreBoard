package com.smalko.scoreboard.scoreboard.entity;

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
    @JoinColumn(name = "player1_id")
    private Players playersOneId;
    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Players playersTwoId;

    @ManyToOne
    @JoinColumn(name = "winner")
    private Players winner;
}
