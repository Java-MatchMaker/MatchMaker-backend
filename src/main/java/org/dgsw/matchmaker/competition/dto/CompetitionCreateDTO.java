package org.dgsw.matchmaker.competition.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CompetitionCreateDTO {

    private String title;
    private String sportType;
    private String description;
    private Integer minParticipants;
    private Integer maxParticipants;
    private LocalDate recruitStartDate;
    private LocalDate recruitEndDate;
    private LocalDate competitionStartDate;
    private LocalDate competitionEndDate;
    private String location;
    private String competitionType;
}