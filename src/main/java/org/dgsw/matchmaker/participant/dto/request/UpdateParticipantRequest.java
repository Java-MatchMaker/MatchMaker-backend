package org.dgsw.matchmaker.participant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateParticipantRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "학번은 필수입니다.")
    private Integer studentId;
}
