package org.dgsw.matchmaker.competition.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.common.exception.ResourceNotFoundException;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.dto.CompetitionResponse;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.bracket.repository.BracketRepository;
import org.dgsw.matchmaker.bracket.repository.LeagueMatchRepository;
import org.dgsw.matchmaker.bracket.repository.TournamentMatchRepository;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final BracketRepository bracketRepository;
    private final LeagueMatchRepository leagueMatchRepository;
    private final TournamentMatchRepository tournamentMatchRepository;
    private final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public CompetitionCreateResponse createCompetition(CompetitionCreateRequest request) {
        Competition competition = Competition.createFrom(request);
        return CompetitionCreateResponse.from(competitionRepository.save(competition));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompetitionResponse> getCompetitions() {
        return competitionRepository.findAll().stream()
                .map(CompetitionResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompetitionResponse getCompetition(Long id) {
        return CompetitionResponse.from(findCompetition(id));
    }

    @Override
    @Transactional
    public void deleteCompetition(Long id) {
        Competition competition = findCompetition(id);
        leagueMatchRepository.deleteAllByBracket_Competition_Id(id);
        tournamentMatchRepository.deleteAllByBracket_Competition_Id(id);
        bracketRepository.deleteAllByCompetition_Id(id);
        participantRepository.deleteAllByCompetition_Id(id);
        competitionRepository.delete(competition);
    }

    private Competition findCompetition(Long id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("대회를 찾을 수 없습니다. id=" + id));
    }
}
