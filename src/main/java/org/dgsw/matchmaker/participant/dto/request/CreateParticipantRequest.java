package org.dgsw.matchmaker.participant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateParticipantRequest {
    @NotBlank
    private String name;

    @NotNull
    private Integer studentId;

    @NotNull
    private Long competitionId;
}
