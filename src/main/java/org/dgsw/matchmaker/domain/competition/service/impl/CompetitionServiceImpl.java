package org.dgsw.matchmaker.domain.competition.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.domain.competition.domain.entity.CompetitionEntity;
import org.dgsw.matchmaker.domain.competition.domain.enums.CompetitionStatus;
import org.dgsw.matchmaker.domain.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.domain.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.domain.competition.service.CompetitionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Transactional
    public CompetitionEntity createCompetition(CompetitionCreateRequestDTO dto) {
        CompetitionEntity competition = new CompetitionEntity();

        competition.setTitle(dto.getTitle());
        competition.setSportType(dto.getSportType());
        competition.setDescription(dto.getDescription());
        competition.setMinParticipants(dto.getMinParticipants());
        competition.setMaxParticipants(dto.getMaxParticipants());
        competition.setRecruitStartDate(dto.getRecruitStartDate());
        competition.setRecruitEndDate(dto.getRecruitEndDate());
        competition.setCompetitionStartDate(dto.getCompetitionStartDate());
        competition.setCompetitionEndDate(dto.getCompetitionEndDate());
        competition.setLocation(dto.getLocation());
        competition.setCompetitionType(dto.getCompetitionType());

        competition.setStatus(CompetitionStatus.IN_PROGRESS);

        return competitionRepository.save(competition);
    }
}