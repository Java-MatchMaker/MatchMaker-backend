package org.dgsw.matchmaker.competition.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.dgsw.matchmaker.competition.entity.CompetitionEntity;
import org.dgsw.matchmaker.competition.type.CompetitionSportType;
import org.dgsw.matchmaker.competition.type.CompetitionType;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
public class CompetitionCreateResponseDTO {
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

    public static CompetitionCreateResponseDTO to(CompetitionEntity entity) {
        return new CompetitionCreateResponseDTO(
                entity.getTitle(),
                entity.getSportType(),
                entity.getDescription(),
                entity.getMinParticipants(),
                entity.getMaxParticipants(),
                entity.getRecruitStartDate(),
                entity.getRecruitEndDate(),
                entity.getCompetitionStartDate(),
                entity.getCompetitionEndDate(),
                entity.getLocation(),
                entity.getCompetitionType()
        );
    }
}
