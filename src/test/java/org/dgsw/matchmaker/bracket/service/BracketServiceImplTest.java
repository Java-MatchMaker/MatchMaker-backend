package org.dgsw.matchmaker.bracket.service;

import org.dgsw.matchmaker.bracket.dto.BracketCreateRequest;
import org.dgsw.matchmaker.bracket.dto.BracketResponse;
import org.dgsw.matchmaker.bracket.dto.BracketTournamentMatchPatchRequest;
import org.dgsw.matchmaker.bracket.dto.BracketUpdateRequest;
import org.dgsw.matchmaker.bracket.dto.TournamentMatchResponse;
import org.dgsw.matchmaker.bracket.enums.BracketType;
import org.dgsw.matchmaker.bracket.enums.TournamentRound;
import org.dgsw.matchmaker.bracket.repository.BracketRepository;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.enums.CompetitionSportType;
import org.dgsw.matchmaker.competition.enums.CompetitionType;
import org.dgsw.matchmaker.competition.repository.CompetitionRepository;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.dgsw.matchmaker.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class BracketServiceImplTest {

    @Autowired
    private BracketService bracketService;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private BracketRepository bracketRepository;

    @Test
    void createLeagueBracketUsesCompetitionTypeAndAllParticipants() {
        Long competitionId = createCompetition(CompetitionType.LEAGUE).getId();
        saveParticipants(competitionId, 4);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);

        BracketResponse response = bracketService.createBracket(request);

        assertEquals(BracketType.LEAGUE, response.getBracketType());
        assertEquals(4, response.getParticipantCount());
        assertEquals(6, response.getLeagueMatches().size());
        assertNull(response.getTournamentMatches());
    }

    @Test
    void createTournamentBracketUsesCompetitionTypeForFourTeams() {
        Long competitionId = createCompetition(CompetitionType.TOURNAMENT).getId();
        saveParticipants(competitionId, 4);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);

        BracketResponse response = bracketService.createBracket(request);

        assertEquals(BracketType.TOURNAMENT, response.getBracketType());
        assertNull(response.getLeagueMatches());
        assertEquals(3, response.getTournamentMatches().size());
        assertEquals(2, countRound(response, TournamentRound.SEMI_FINAL));
        assertEquals(1, countRound(response, TournamentRound.FINAL));
    }

    @Test
    void createTournamentBracketGeneratesQuarterFinalStructureForEightTeams() {
        Long competitionId = createCompetition(CompetitionType.TOURNAMENT).getId();
        saveParticipants(competitionId, 8);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);

        BracketResponse response = bracketService.createBracket(request);

        assertEquals(8, response.getParticipantCount());
        assertEquals(7, response.getTournamentMatches().size());
        assertEquals(4, countRound(response, TournamentRound.QUARTER_FINAL));
        assertEquals(2, countRound(response, TournamentRound.SEMI_FINAL));
        assertEquals(1, countRound(response, TournamentRound.FINAL));
    }

    @Test
    void createTournamentBracketFor16ParticipantsStartsAtRoundOf16() {
        Long competitionId = createCompetition(CompetitionType.TOURNAMENT).getId();
        saveParticipants(competitionId, 16);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);

        BracketResponse response = bracketService.createBracket(request);

        assertEquals(16, response.getParticipantCount());
        assertEquals(8, countRound(response, TournamentRound.ROUND_OF_16));
        assertEquals(15, response.getTournamentMatches().size());
    }

    @Test
    void updateBracketRegeneratesMatchesWithSelectedParticipants() {
        Long competitionId = createCompetition(CompetitionType.LEAGUE).getId();
        List<Long> participantIds = saveParticipants(competitionId, 4);

        BracketCreateRequest createRequest = new BracketCreateRequest();
        createRequest.setCompetitionId(competitionId);
        BracketResponse created = bracketService.createBracket(createRequest);

        BracketUpdateRequest updateRequest = new BracketUpdateRequest();
        updateRequest.setParticipantIds(participantIds.subList(0, 2));

        BracketResponse updated = bracketService.updateBracket(created.getId(), updateRequest);

        assertEquals(created.getId(), updated.getId());
        assertEquals(BracketType.LEAGUE, updated.getBracketType());
        assertEquals(2, updated.getParticipantCount());
        assertEquals(1, updated.getLeagueMatches().size());
    }

    @Test
    void patchTournamentMatchRecordsWinnerOnly() {
        Long competitionId = createCompetition(CompetitionType.TOURNAMENT).getId();
        saveParticipants(competitionId, 2);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);
        BracketResponse bracket = bracketService.createBracket(request);

        Long matchId = bracket.getTournamentMatches().getFirst().getId();
        Long winnerId = bracket.getTournamentMatches().getFirst().getHomeParticipant().getId();

        BracketTournamentMatchPatchRequest patchRequest = new BracketTournamentMatchPatchRequest();
        patchRequest.setWinnerParticipantId(winnerId);

        TournamentMatchResponse patched = bracketService.patchTournamentMatch(matchId, patchRequest);

        assertEquals(matchId, patched.getId());
        assertEquals(winnerId, patched.getWinner().getId());
    }

    @Test
    void patchTournamentMatchThrowsWhenWinnerIsNotInMatch() {
        Long competitionId = createCompetition(CompetitionType.TOURNAMENT).getId();
        saveParticipants(competitionId, 4);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);
        BracketResponse bracket = bracketService.createBracket(request);

        Long matchId = bracket.getTournamentMatches().getFirst().getId();
        Long outsiderId = bracket.getTournamentMatches().get(1).getHomeParticipant().getId();

        BracketTournamentMatchPatchRequest patchRequest = new BracketTournamentMatchPatchRequest();
        patchRequest.setWinnerParticipantId(outsiderId);

        assertThrows(IllegalArgumentException.class, () ->
                bracketService.patchTournamentMatch(matchId, patchRequest));
    }

    @Test
    void deleteBracketRemovesBracket() {
        Long competitionId = createCompetition(CompetitionType.LEAGUE).getId();
        saveParticipants(competitionId, 4);

        BracketCreateRequest request = new BracketCreateRequest();
        request.setCompetitionId(competitionId);
        BracketResponse created = bracketService.createBracket(request);

        bracketService.deleteBracket(created.getId());

        assertTrue(bracketRepository.findById(created.getId()).isEmpty());
    }

    private long countRound(BracketResponse response, TournamentRound round) {
        return response.getTournamentMatches().stream()
                .filter(match -> match.getRound() == round)
                .count();
    }

    private CompetitionCreateResponse createCompetition(CompetitionType competitionType) {
        CompetitionCreateRequest request = new CompetitionCreateRequest();
        request.setTitle(competitionType.name() + " 테스트 대회");
        request.setSportType(CompetitionSportType.SOCCER);
        request.setDescription("테스트");
        request.setMinParticipants(2);
        request.setMaxParticipants(16);
        request.setRecruitStartDate(LocalDate.of(2026, 6, 22));
        request.setRecruitEndDate(LocalDate.of(2026, 6, 30));
        request.setCompetitionStartDate(LocalDate.of(2026, 7, 10));
        request.setCompetitionEndDate(LocalDate.of(2026, 7, 12));
        request.setLocation("대구");
        request.setCompetitionType(competitionType);
        return competitionService.createCompetition(request);
    }

    private List<Long> saveParticipants(Long competitionId, int count) {
        return java.util.stream.IntStream.rangeClosed(1, count)
                .mapToObj(i -> {
                    Participant participant = new Participant();
                    participant.setName("팀" + i);
                    participant.setStudentId(20260000 + i);
                    participant.setCompetition(competitionRepository.getReferenceById(competitionId));
                    return participantRepository.save(participant).getId();
                })
                .toList();
    }
}
