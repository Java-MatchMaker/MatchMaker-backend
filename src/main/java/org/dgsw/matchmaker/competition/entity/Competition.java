package org.dgsw.matchmaker.competition.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionStatus;
import org.dgsw.matchmaker.competition.enums.CompetitionType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "competition")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionSportType sportType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer minParticipants;

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false)
    private LocalDate recruitStartDate;

    @Column(nullable = false)
    private LocalDate recruitEndDate;

    @Column(nullable = false)
    private LocalDate competitionStartDate;

    @Column(nullable = false)
    private LocalDate competitionEndDate;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionType competitionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionStatus status;

    public static Competition createFrom(CompetitionCreateRequest request) {
        Competition competition = new Competition();
        competition.title = request.getTitle();
        competition.sportType = request.getSportType();
        competition.description = request.getDescription();
        competition.minParticipants = request.getMinParticipants();
        competition.maxParticipants = request.getMaxParticipants();
        competition.recruitStartDate = request.getRecruitStartDate();
        competition.recruitEndDate = request.getRecruitEndDate();
        competition.competitionStartDate = request.getCompetitionStartDate();
        competition.competitionEndDate = request.getCompetitionEndDate();
        competition.location = request.getLocation();
        competition.competitionType = request.getCompetitionType();
        competition.status = CompetitionStatus.BEFORE_START;
        return competition;
    }
}
