package org.dgsw.matchmaker.participant.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;

    @Override
    @Transactional
    public ParticipantResponse createParticipant(CreateParticipantRequest request) {
        Competition competition = competitionRepository.findById(request.getCompetitionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "대회를 찾을 수 없습니다."
                ));

        Participant participant = new Participant();
        participant.setName(request.getName());
        participant.setStudentId(request.getStudentId());
        participant.setCompetition(competition);

        Participant savedParticipant = participantRepository.save(participant);
        return ParticipantResponse.from(savedParticipant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantResponse> getParticipantsByCompetition(Long competitionId) {
        if (!competitionRepository.existsById(competitionId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "대회를 찾을 수 없습니다."
            );
        }

        return participantRepository
                .findAllByCompetition_IdOrderByStudentIdAsc(competitionId)
                .stream()
                .map(ParticipantResponse::from)
                .toList();
    }
}
