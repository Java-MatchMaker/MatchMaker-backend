package org.dgsw.matchmaker.competition.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionStatus;
import org.dgsw.matchmaker.competition.enums.CompetitionType;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CompetitionServiceImplTest {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Test
    void createCompetitionSetsBeforeStartStatus() {
        CompetitionCreateRequest request = new CompetitionCreateRequest();
        request.setTitle("테스트 대회");
        request.setSportType(CompetitionSportType.SOCCER);
        request.setDescription("상태 검증");
        request.setMinParticipants(8);
        request.setMaxParticipants(16);
        request.setRecruitStartDate(LocalDate.of(2026, 6, 22));
        request.setRecruitEndDate(LocalDate.of(2026, 6, 30));
        request.setCompetitionStartDate(LocalDate.of(2026, 7, 10));
        request.setCompetitionEndDate(LocalDate.of(2026, 7, 12));
        request.setLocation("대구");
        request.setCompetitionType(CompetitionType.LEAGUE);

        CompetitionCreateResponse response = competitionService.createCompetition(request);

        assertNotNull(response.getId());
        assertEquals(CompetitionStatus.BEFORE_START, response.getStatus());
        assertEquals("테스트 대회", response.getTitle());
    }

    @Test
    void responseSerializesFields() throws Exception {
        Competition competition = Competition.createFrom(createSampleRequest());
        competitionRepository.save(competition);

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        String json = objectMapper.writeValueAsString(
                CompetitionCreateResponse.from(competitionRepository.findAll().getFirst())
        );

        assertTrue(json.contains("\"title\":\"테스트 대회\""));
        assertTrue(json.contains("\"status\":\"BEFORE_START\""));
    }

    private CompetitionCreateRequest createSampleRequest() {
        CompetitionCreateRequest request = new CompetitionCreateRequest();
        request.setTitle("테스트 대회");
        request.setSportType(CompetitionSportType.SOCCER);
        request.setDescription("응답 직렬화");
        request.setMinParticipants(8);
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
