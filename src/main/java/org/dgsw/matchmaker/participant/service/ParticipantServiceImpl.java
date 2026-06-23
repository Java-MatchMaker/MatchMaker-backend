package org.dgsw.matchmaker.participant.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.common.exception.ResourceNotFoundException;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;

    @Override
    @Transactional
    public ParticipantResponse createParticipant(CreateParticipantRequest request) {
        Competition competition = competitionRepository.findById(request.getCompetitionId())
                .orElseThrow(() -> new ResourceNotFoundException("대회를 찾을 수 없습니다. id=" + request.getCompetitionId()));

        Participant participant = new Participant();
        participant.setName(request.getName());
        participant.setStudentId(request.getStudentId());
        participant.setCompetition(competition);

        return ParticipantResponse.from(participantRepository.save(participant));
    }
}
