package org.dgsw.matchmaker.bracket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BracketTournamentMatchPatchRequest {

    @NotNull(message = "승자 참가자 ID는 필수입니다.")
    private Long winnerParticipantId;
}
