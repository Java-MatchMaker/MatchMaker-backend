package org.dgsw.matchmaker.bracket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BracketCreateRequest {

    @NotNull(message = "대회 ID는 필수입니다.")
    private Long competitionId;
}
