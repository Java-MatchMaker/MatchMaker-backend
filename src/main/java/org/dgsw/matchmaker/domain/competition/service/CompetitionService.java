package org.dgsw.matchmaker.domain.competition.service;

import org.dgsw.matchmaker.domain.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.domain.competition.domain.entity.CompetitionEntity;

public interface CompetitionService {
    CompetitionEntity createCompetition(CompetitionCreateRequestDTO dto);
}