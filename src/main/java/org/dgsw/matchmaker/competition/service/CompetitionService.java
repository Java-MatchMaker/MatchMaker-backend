package org.dgsw.matchmaker.competition.service;

import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;

public interface CompetitionService {

    CompetitionCreateResponse createCompetition(CompetitionCreateRequest request);

    CompetitionCreateResponse updateCompetition(CompetitionCreateRequest request);
}
