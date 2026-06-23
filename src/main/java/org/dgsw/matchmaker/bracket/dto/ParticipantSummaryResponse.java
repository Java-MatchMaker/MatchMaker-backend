package org.dgsw.matchmaker.bracket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dgsw.matchmaker.participant.entity.Participant;

@Getter
@AllArgsConstructor
public class ParticipantSummaryResponse {

    private Long id;
    private String name;
    private Integer studentId;

    public static ParticipantSummaryResponse from(Participant participant) {
        if (participant == null) {
            return null;
        }
        return new ParticipantSummaryResponse(
                participant.getId(),
                participant.getName(),
                participant.getStudentId()
        );
    }
}
