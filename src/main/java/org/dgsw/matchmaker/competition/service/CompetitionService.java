package org.dgsw.matchmaker.competition.service;

import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.dto.CompetitionResponse;

import java.util.List;

public interface CompetitionService {

    CompetitionCreateResponse createCompetition(CompetitionCreateRequest request);

    List<CompetitionResponse> getCompetitions();

    CompetitionResponse getCompetition(Long id);

    void deleteCompetition(Long id);
}
