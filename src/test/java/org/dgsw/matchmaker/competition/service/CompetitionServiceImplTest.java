package org.dgsw.matchmaker.competition.service;

import org.dgsw.matchmaker.common.exception.ResourceNotFoundException;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.dto.CompetitionResponse;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionType;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CompetitionServiceImplTest {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void getCompetitionsReturnsSavedCompetition() {
        CompetitionCreateResponse created = competitionService.createCompetition(sampleRequest("조회 테스트"));

        List<CompetitionResponse> responses = competitionService.getCompetitions();

        assertFalse(responses.isEmpty());
        assertEquals(created.getId(), responses.getFirst().getId());
        assertEquals("조회 테스트", responses.getFirst().getTitle());
    }

    @Test
    void getCompetitionReturnsDetail() {
        CompetitionCreateResponse created = competitionService.createCompetition(sampleRequest("상세 조회"));

        CompetitionResponse response = competitionService.getCompetition(created.getId());

        assertEquals(created.getId(), response.getId());
        assertEquals("상세 조회", response.getTitle());
    }

    @Test
    void deleteCompetitionRemovesCompetitionAndParticipants() {
        CompetitionCreateResponse created = competitionService.createCompetition(sampleRequest("삭제 테스트"));
        Participant participant = new Participant();
        participant.setName("A팀");
        participant.setStudentId(20261001);
        participant.setCompetition(competitionRepository.getReferenceById(created.getId()));
        participant = participantRepository.save(participant);

        competitionService.deleteCompetition(created.getId());

        assertFalse(competitionRepository.existsById(created.getId()));
        assertFalse(participantRepository.existsById(participant.getId()));
    }

    @Test
    void getCompetitionThrowsWhenNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> competitionService.getCompetition(999L));
    }

    private CompetitionCreateRequest sampleRequest(String title) {
        CompetitionCreateRequest request = new CompetitionCreateRequest();
        request.setTitle(title);
        request.setSportType(CompetitionSportType.SOCCER);
        request.setDescription("테스트");
        request.setMinParticipants(2);
        request.setMaxParticipants(16);
        request.setRecruitStartDate(LocalDate.of(2026, 6, 22));
        request.setRecruitEndDate(LocalDate.of(2026, 6, 30));
        request.setCompetitionStartDate(LocalDate.of(2026, 7, 10));
        request.setCompetitionEndDate(LocalDate.of(2026, 7, 12));
        request.setLocation("대구");
        request.setCompetitionType(CompetitionType.LEAGUE);
        return request;
    }
}
