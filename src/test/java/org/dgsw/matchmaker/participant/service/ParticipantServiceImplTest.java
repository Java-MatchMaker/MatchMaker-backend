package org.dgsw.matchmaker.participant.service;

import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionType;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.request.UpdateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ParticipantServiceImplTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private CompetitionService competitionService;

    @Test
    void createParticipantLinksToCompetition() {
        CompetitionCreateResponse competition = competitionService.createCompetition(sampleCompetitionRequest());

        CreateParticipantRequest request = new CreateParticipantRequest();
        request.setName("A팀");
        request.setStudentId(20261001);
        request.setCompetitionId(competition.getId());

        ParticipantResponse response = participantService.createParticipant(request);

        assertNotNull(response.getId());
        assertEquals("A팀", response.getName());
        assertEquals(20261001, response.getStudentId());
        assertEquals(competition.getId(), response.getCompetitionId());
    }

    @Test
    void updateParticipantChangesNameAndStudentId() {
        CompetitionCreateResponse competition = competitionService.createCompetition(sampleCompetitionRequest());

        CreateParticipantRequest createRequest = new CreateParticipantRequest();
        createRequest.setName("A팀");
        createRequest.setStudentId(20261001);
        createRequest.setCompetitionId(competition.getId());
        ParticipantResponse created = participantService.createParticipant(createRequest);

        UpdateParticipantRequest updateRequest = new UpdateParticipantRequest();
        updateRequest.setName("A팀 수정");
        updateRequest.setStudentId(20261002);

        ParticipantResponse updated = participantService.updateParticipant(created.getId(), updateRequest);

        assertEquals(created.getId(), updated.getId());
        assertEquals("A팀 수정", updated.getName());
        assertEquals(20261002, updated.getStudentId());
        assertEquals(competition.getId(), updated.getCompetitionId());
    }

    @Test
    void updateParticipantThrowsWhenDuplicateStudentIdExists() {
        CompetitionCreateResponse competition = competitionService.createCompetition(sampleCompetitionRequest());

        CreateParticipantRequest first = new CreateParticipantRequest();
        first.setName("A팀");
        first.setStudentId(20261001);
        first.setCompetitionId(competition.getId());
        participantService.createParticipant(first);

        ParticipantResponse second = participantService.createParticipant(createRequest(
                "B팀", 20261002, competition.getId()));

        UpdateParticipantRequest updateRequest = new UpdateParticipantRequest();
        updateRequest.setName("B팀 수정");
        updateRequest.setStudentId(20261001);

        assertThrows(ResponseStatusException.class, () ->
                participantService.updateParticipant(second.getId(), updateRequest));
    }

    private CreateParticipantRequest createRequest(String name, Integer studentId, Long competitionId) {
        CreateParticipantRequest request = new CreateParticipantRequest();
        request.setName(name);
        request.setStudentId(studentId);
        request.setCompetitionId(competitionId);
        return request;
    }

    private CompetitionCreateRequest sampleCompetitionRequest() {
        CompetitionCreateRequest request = new CompetitionCreateRequest();
        request.setTitle("참가자 테스트 대회");
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
