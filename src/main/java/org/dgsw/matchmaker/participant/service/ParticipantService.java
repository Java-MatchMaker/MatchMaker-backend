package org.dgsw.matchmaker.participant.service;

import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;

import java.util.List;

public interface ParticipantService {

    ParticipantResponse createParticipant(CreateParticipantRequest request);

    ParticipantResponse getParticipant(Long participantId);

    List<ParticipantResponse> getParticipantsByCompetition(Long competitionId);

    void deleteParticipant(Long competitionId, Long participantId);
}
