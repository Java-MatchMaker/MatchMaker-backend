package org.dgsw.matchmaker.bracket.service;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.bracket.dto.BracketCreateRequest;
import org.dgsw.matchmaker.bracket.dto.BracketResponse;
import org.dgsw.matchmaker.bracket.dto.BracketTournamentMatchPatchRequest;
import org.dgsw.matchmaker.bracket.dto.BracketUpdateRequest;
import org.dgsw.matchmaker.bracket.dto.TournamentMatchResponse;
import org.dgsw.matchmaker.bracket.entity.Bracket;
import org.dgsw.matchmaker.bracket.entity.LeagueMatch;
import org.dgsw.matchmaker.bracket.entity.TournamentMatch;
import org.dgsw.matchmaker.bracket.enums.BracketType;
import org.dgsw.matchmaker.bracket.repository.BracketRepository;
import org.dgsw.matchmaker.bracket.repository.LeagueMatchRepository;
import org.dgsw.matchmaker.bracket.repository.TournamentMatchRepository;
import org.dgsw.matchmaker.common.exception.ResourceNotFoundException;
import org.dgsw.matchmaker.competition.entity.Competition;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BracketServiceImpl implements BracketService {

    private final BracketRepository bracketRepository;
    private final LeagueMatchRepository leagueMatchRepository;
    private final TournamentMatchRepository tournamentMatchRepository;
    private final CompetitionRepository competitionRepository;
    private final ParticipantRepository participantRepository;
    private final BracketGenerator bracketGenerator;

    @Override
    @Transactional
    public BracketResponse createBracket(BracketCreateRequest request) {
        Competition competition = findCompetition(request.getCompetitionId());
        BracketType bracketType = BracketType.from(competition.getCompetitionType());
        List<Participant> participants = resolveParticipants(competition.getId(), null);

        bracketGenerator.validateParticipantCount(bracketType, participants.size());

        Bracket bracket = bracketRepository.save(
                Bracket.create(competition, bracketType, participants.size())
        );

        return buildResponse(bracket, participants);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BracketResponse> getBrackets(Long competitionId) {
        List<Bracket> brackets = competitionId == null
                ? bracketRepository.findAll()
                : bracketRepository.findAllByCompetition_IdOrderByIdAsc(competitionId);

        return brackets.stream()
                .map(this::loadResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BracketResponse getBracket(Long id) {
        return loadResponse(findBracket(id));
    }

    @Override
    @Transactional
    public BracketResponse updateBracket(Long id, BracketUpdateRequest request) {
        Bracket bracket = findBracket(id);
        Competition competition = bracket.getCompetition();
        BracketType bracketType = BracketType.from(competition.getCompetitionType());

        List<Participant> participants = resolveParticipants(
                competition.getId(),
                request.getParticipantIds()
        );

        bracketGenerator.validateParticipantCount(bracketType, participants.size());
        deleteMatches(bracket.getId());

        bracket.update(bracketType, participants.size());
        return buildResponse(bracket, participants);
    }

    @Override
    @Transactional
    public TournamentMatchResponse patchTournamentMatch(
            Long matchId,
            BracketTournamentMatchPatchRequest request
    ) {
        TournamentMatch match = tournamentMatchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("토너먼트 경기를 찾을 수 없습니다. id=" + matchId));

        Participant winner = findWinnerParticipant(match, request.getWinnerParticipantId());
        match.recordWinner(winner);

        return TournamentMatchResponse.from(match);
    }

    @Override
    @Transactional
    public void deleteBracket(Long id) {
        Bracket bracket = findBracket(id);
        deleteMatches(bracket.getId());
        bracketRepository.delete(bracket);
    }

    private BracketResponse buildResponse(Bracket bracket, List<Participant> participants) {
        if (bracket.getBracketType() == BracketType.LEAGUE) {
            List<LeagueMatch> matches = bracketGenerator.generateLeagueMatches(bracket, participants);
            leagueMatchRepository.saveAll(matches);
            return BracketResponse.fromLeague(bracket, matches);
        }

        List<TournamentMatch> matches = bracketGenerator.generateTournamentMatches(bracket, participants);
        tournamentMatchRepository.saveAll(matches);
        return BracketResponse.fromTournament(bracket, matches);
    }

    private BracketResponse loadResponse(Bracket bracket) {
        if (bracket.getBracketType() == BracketType.LEAGUE) {
            List<LeagueMatch> matches = leagueMatchRepository.findAllByBracket_IdOrderByMatchNumberAsc(bracket.getId());
            return BracketResponse.fromLeague(bracket, matches);
        }

        List<TournamentMatch> matches = tournamentMatchRepository.findAllByBracket_IdOrderByRoundAscMatchOrderAsc(bracket.getId());
        return BracketResponse.fromTournament(bracket, matches);
    }

    private List<Participant> resolveParticipants(Long competitionId, List<Long> participantIds) {
        if (participantIds == null || participantIds.isEmpty()) {
            List<Participant> participants = participantRepository.findAllByCompetition_IdOrderByIdAsc(competitionId);
            if (participants.isEmpty()) {
                throw new IllegalArgumentException("대회에 등록된 참가자가 없습니다.");
            }
            return participants;
        }

        List<Participant> participants = participantRepository.findAllByCompetition_IdAndIdInOrderByIdAsc(
                competitionId,
                participantIds
        );

        if (participants.size() != participantIds.size()) {
            throw new IllegalArgumentException("존재하지 않거나 해당 대회에 속하지 않은 참가자가 포함되어 있습니다.");
        }

        return participants;
    }

    private Competition findCompetition(Long id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("대회를 찾을 수 없습니다. id=" + id));
    }

    private Bracket findBracket(Long id) {
        return bracketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("대진표를 찾을 수 없습니다. id=" + id));
    }

    private void deleteMatches(Long bracketId) {
        leagueMatchRepository.deleteAllByBracket_Id(bracketId);
        tournamentMatchRepository.deleteAllByBracket_Id(bracketId);
    }

    private Participant findWinnerParticipant(TournamentMatch match, Long winnerParticipantId) {
        Participant homeParticipant = match.getHomeParticipant();
        Participant awayParticipant = match.getAwayParticipant();

        if (homeParticipant != null && homeParticipant.getId().equals(winnerParticipantId)) {
            return homeParticipant;
        }
        if (awayParticipant != null && awayParticipant.getId().equals(winnerParticipantId)) {
            return awayParticipant;
        }

        throw new IllegalArgumentException("승자는 해당 경기의 참가자여야 합니다.");
    }
}
