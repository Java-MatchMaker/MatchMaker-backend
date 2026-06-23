package org.dgsw.matchmaker.participant.service;

import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.request.UpdateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;

public interface ParticipantService {

    ParticipantResponse createParticipant(CreateParticipantRequest request);

    ParticipantResponse updateParticipant(Long id, UpdateParticipantRequest request);
}
