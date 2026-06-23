package org.dgsw.matchmaker.competition.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionType;

import java.time.LocalDate;

@Getter
@Setter
public class CompetitionCreateRequest {
    @NotNull(message = "대회 ID는 필수입니다.")
    private Long id;

    @NotBlank(message = "대회명은 필수입니다.")
    private String title;

    @NotNull(message = "종목은 필수입니다.")
    private CompetitionSportType sportType;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotNull(message = "최소 참가 인원은 필수입니다.")
    @Min(value = 1, message = "최소 참가 인원은 1명 이상이어야 합니다.")
    private Integer minParticipants;

    @NotNull(message = "최대 참가 인원은 필수입니다.")
    @Min(value = 2, message = "최대 참가 인원은 2명 이상이어야 합니다.")
    private Integer maxParticipants;

    @NotNull(message = "모집 시작일은 필수입니다.")
    private LocalDate recruitStartDate;

    @NotNull(message = "모집 종료일은 필수입니다.")
    private LocalDate recruitEndDate;

    @NotNull(message = "대회 시작일은 필수입니다.")
    private LocalDate competitionStartDate;

    @NotNull(message = "대회 종료일은 필수입니다.")
    private LocalDate competitionEndDate;

    @NotBlank(message = "장소는 필수입니다.")
    private String location;

    @NotNull(message = "대회 유형은 필수입니다.")
    private CompetitionType competitionType;
}
