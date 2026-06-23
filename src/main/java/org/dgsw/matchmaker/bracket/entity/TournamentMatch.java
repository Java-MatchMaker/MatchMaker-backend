package org.dgsw.matchmaker.bracket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dgsw.matchmaker.bracket.enums.TournamentRound;
import org.dgsw.matchmaker.participant.entity.Participant;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tournament_match")
public class TournamentMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bracket_id", nullable = false)
    private Bracket bracket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TournamentRound round;

    @Column(nullable = false)
    private int matchOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_participant_id")
    private Participant homeParticipant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_participant_id")
    private Participant awayParticipant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_participant_id")
    private Participant winner;

    public static TournamentMatch create(Bracket bracket, TournamentRound round, int matchOrder) {
        TournamentMatch match = new TournamentMatch();
        match.bracket = bracket;
        match.round = round;
        match.matchOrder = matchOrder;
        return match;
    }

    public void assignHomeParticipant(Participant participant) {
        this.homeParticipant = participant;
    }

    public void assignAwayParticipant(Participant participant) {
        this.awayParticipant = participant;
    }
}
