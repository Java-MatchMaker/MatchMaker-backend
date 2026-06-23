package org.dgsw.matchmaker.participant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.participant.entity.Participant;

@Getter
@AllArgsConstructor
public class ParticipantResponse {
    private Long id;
    private String name;
    private Integer studentId;
    private Competition competition;

    public static ParticipantResponse from(Participant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getName(),
                participant.getStudentId(),
                participant.getCompetition()
        );
    }
}
