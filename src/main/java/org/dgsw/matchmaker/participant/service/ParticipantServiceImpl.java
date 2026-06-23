package org.dgsw.matchmaker.participant.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public ParticipantResponse createParticipant(CreateParticipantRequest request) {
        Participant participant = new Participant();
        participant.setName(request.getName());
        participant.setStudentId(request.getStudentId());
        participant.setCompetition(request.getCompetition());

        Participant savedParticipant = participantRepository.save(participant);
        return ParticipantResponse.from(savedParticipant);
    }
}
