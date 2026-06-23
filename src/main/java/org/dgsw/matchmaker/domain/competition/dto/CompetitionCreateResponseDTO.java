package org.dgsw.matchmaker.domain.competition.dto;

import lombok.AllArgsConstructor;
import org.dgsw.matchmaker.domain.competition.domain.entity.CompetitionEntity;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionSportType;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionStatus;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CompetitionCreateResponseDTO {
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

    public static CompetitionCreateResponseDTO to(CompetitionEntity entity) {
        return new CompetitionCreateResponseDTO(
                entity.getId(),
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
                entity.getCompetitionType(),
                entity.getStatus()
        );
    }
}
