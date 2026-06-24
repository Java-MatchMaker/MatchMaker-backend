package org.dgsw.matchmaker.bracket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dgsw.matchmaker.participant.entity.Participant;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "league_match")
public class LeagueMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bracket_id", nullable = false)
    private Bracket bracket;

    private int matchNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_participant_id")
    private Participant homeParticipant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_participant_id")
    private Participant awayParticipant;

    public static LeagueMatch create(
            Bracket bracket,
            int matchNumber,
            Participant homeParticipant,
            Participant awayParticipant
    ) {
        LeagueMatch match = new LeagueMatch();
        match.bracket = bracket;
        match.matchNumber = matchNumber;
        match.homeParticipant = homeParticipant;
        match.awayParticipant = awayParticipant;
        return match;
    }
}
