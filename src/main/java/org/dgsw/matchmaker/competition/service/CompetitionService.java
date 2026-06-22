package org.dgsw.matchmaker.competition.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.competition.entity.CompetitionCreateEntity;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.competition.type.CompetitionStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public CompetitionCreateEntity createCompetition(CompetitionCreateRequestDTO dto) {
        CompetitionCreateEntity competition = new CompetitionCreateEntity();

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