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
import org.dgsw.matchmaker.bracket.enums.BracketType;
import org.dgsw.matchmaker.competition.entity.Competition;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bracket")
public class Bracket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BracketType bracketType;

    @Column(nullable = false)
    private int participantCount;

    public static Bracket create(Competition competition, BracketType bracketType, int participantCount) {
        Bracket bracket = new Bracket();
        bracket.competition = competition;
        bracket.bracketType = bracketType;
        bracket.participantCount = participantCount;
        return bracket;
    }

    public void update(BracketType bracketType, int participantCount) {
        this.bracketType = bracketType;
        this.participantCount = participantCount;
    }
}
