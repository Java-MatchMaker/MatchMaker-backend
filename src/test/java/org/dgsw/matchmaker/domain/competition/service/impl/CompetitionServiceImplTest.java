package org.dgsw.matchmaker.domain.competition.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dgsw.matchmaker.domain.competition.domain.entity.CompetitionEntity;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionSportType;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionStatus;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionType;
import org.dgsw.matchmaker.domain.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.domain.competition.dto.CompetitionCreateResponseDTO;
import org.dgsw.matchmaker.domain.competition.service.CompetitionService;
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

    @Test
    void createCompetitionSetsBeforeStartStatus() {
        CompetitionCreateRequestDTO dto = new CompetitionCreateRequestDTO();
        dto.setTitle("테스트 대회");
        dto.setSportType(CompetitionSportType.SOCCER);
        dto.setDescription("상태 검증");
        dto.setMinParticipants(8);
        dto.setMaxParticipants(16);
        dto.setRecruitStartDate(LocalDate.of(2026, 6, 22));
        dto.setRecruitEndDate(LocalDate.of(2026, 6, 30));
        dto.setCompetitionStartDate(LocalDate.of(2026, 7, 10));
        dto.setCompetitionEndDate(LocalDate.of(2026, 7, 12));
        dto.setLocation("대구");
        dto.setCompetitionType(CompetitionType.LEAGUE);

        CompetitionEntity competition = competitionService.createCompetition(dto);

        assertNotNull(competition.getId());
        assertEquals(CompetitionStatus.BEFORE_START, competition.getStatus());
        assertEquals("테스트 대회", competition.getTitle());
    }

    @Test
    void responseDtoSerializesFields() throws Exception {
        CompetitionEntity entity = new CompetitionEntity();
        entity.setId(1L);
        entity.setTitle("테스트 대회");
        entity.setSportType(CompetitionSportType.SOCCER);
        entity.setDescription("응답 직렬화");
        entity.setMinParticipants(8);
        entity.setMaxParticipants(16);
        entity.setRecruitStartDate(LocalDate.of(2026, 6, 22));
        entity.setRecruitEndDate(LocalDate.of(2026, 6, 30));
        entity.setCompetitionStartDate(LocalDate.of(2026, 7, 10));
        entity.setCompetitionEndDate(LocalDate.of(2026, 7, 12));
        entity.setLocation("대구");
        entity.setCompetitionType(CompetitionType.LEAGUE);
        entity.setStatus(CompetitionStatus.BEFORE_START);

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        String json = objectMapper.writeValueAsString(CompetitionCreateResponseDTO.to(entity));

        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"title\":\"테스트 대회\""));
        assertTrue(json.contains("\"status\":\"BEFORE_START\""));
    }
}
