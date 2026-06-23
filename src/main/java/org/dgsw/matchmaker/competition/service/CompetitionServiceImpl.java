package org.dgsw.matchmaker.competition.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Override
    @Transactional
    public CompetitionCreateResponse createCompetition(CompetitionCreateRequest request) {
        Competition competition = Competition.createFrom(request);
        return CompetitionCreateResponse.from(competitionRepository.save(competition));
    }
}
