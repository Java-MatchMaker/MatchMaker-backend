package org.dgsw.matchmaker.participant.service;

import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionType;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
