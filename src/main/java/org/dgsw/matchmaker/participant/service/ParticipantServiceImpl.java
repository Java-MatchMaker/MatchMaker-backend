package org.dgsw.matchmaker.participant.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.request.UpdateParticipantRequest;
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
        Competition competition = findCompetition(request.getCompetitionId());

        Participant participant = new Participant();
        participant.setName(request.getName());
        participant.setStudentId(request.getStudentId());
        participant.setCompetition(competition);

        return ParticipantResponse.from(participantRepository.save(participant));
    }

    @Override
    @Transactional
    public ParticipantResponse updateParticipant(Long id, UpdateParticipantRequest request) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "참가자를 찾을 수 없습니다."
                ));

        validateDuplicateStudentId(participant, request.getStudentId());

        participant.setName(request.getName());
        participant.setStudentId(request.getStudentId());

        return ParticipantResponse.from(participantRepository.save(participant));
    }

    private Competition findCompetition(Long competitionId) {
        return competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "대회를 찾을 수 없습니다."
                ));
    }

    private void validateDuplicateStudentId(Participant participant, Integer studentId) {
        if (participant.getStudentId().equals(studentId)) {
            return;
        }

        participantRepository.findByCompetition_IdAndStudentId(
                        participant.getCompetition().getId(),
                        studentId
                )
                .filter(existing -> !existing.getId().equals(participant.getId()))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "같은 대회에 이미 등록된 학번입니다."
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ParticipantResponse getParticipant(Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "참가자를 찾을 수 없습니다."
                ));

        return ParticipantResponse.from(participant);
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

    @Override
    @Transactional
    public void deleteParticipant(Long competitionId, Long participantId) {
        if (!competitionRepository.existsById(competitionId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "대회를 찾을 수 없습니다."
            );
        }

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "참가자를 찾을 수 없습니다."
                ));

        if (!participant.getCompetition().getId().equals(competitionId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "참가자를 찾을 수 없습니다."
            );
        }

        participantRepository.delete(participant);
    }
}
