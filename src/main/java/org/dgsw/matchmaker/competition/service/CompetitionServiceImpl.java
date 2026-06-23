package org.dgsw.matchmaker.competition.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    @Transactional
    public CompetitionCreateResponse updateCompetition(CompetitionCreateRequest request) {
        Optional<Competition> optional = competitionRepository.findById(request.getId());
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Competition not found: " + request.getId());
        }

        Competition competition = optional.get();
        competition.setTitle(request.getTitle());

        return CompetitionCreateResponse.from(competitionRepository.save(competition));
    }

}
