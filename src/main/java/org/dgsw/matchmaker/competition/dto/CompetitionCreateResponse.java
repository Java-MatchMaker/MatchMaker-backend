package org.dgsw.matchmaker.competition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionStatus;
import org.dgsw.matchmaker.competition.enums.CompetitionType;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CompetitionCreateResponse {

    private Long id;
    private String title;
    private CompetitionSportType sportType;
    private String description;
    private Integer minParticipants;
    private Integer maxParticipants;
    private LocalDate recruitStartDate;
    private LocalDate recruitEndDate;
    private LocalDate competitionStartDate;
    private LocalDate competitionEndDate;
    private String location;
    private CompetitionType competitionType;
    private CompetitionStatus status;


    public static CompetitionCreateResponse from(Competition competition) {
        return new CompetitionCreateResponse(
                competition.getId(),
                competition.getTitle(),
                competition.getSportType(),
                competition.getDescription(),
                competition.getMinParticipants(),
                competition.getMaxParticipants(),
                competition.getRecruitStartDate(),
                competition.getRecruitEndDate(),
                competition.getCompetitionStartDate(),
                competition.getCompetitionEndDate(),
                competition.getLocation(),
                competition.getCompetitionType(),
                competition.getStatus()
        );
    }
}
