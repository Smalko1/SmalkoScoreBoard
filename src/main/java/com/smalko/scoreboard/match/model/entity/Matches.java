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
@Table(name="Matches")
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Players playersOneId;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Players playersTwoId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Players winner;
}
