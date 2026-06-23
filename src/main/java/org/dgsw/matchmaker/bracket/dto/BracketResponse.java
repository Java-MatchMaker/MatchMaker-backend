package org.dgsw.matchmaker.bracket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dgsw.matchmaker.bracket.entity.Bracket;
import org.dgsw.matchmaker.bracket.entity.LeagueMatch;
import org.dgsw.matchmaker.bracket.entity.TournamentMatch;
import org.dgsw.matchmaker.bracket.enums.BracketType;

import java.util.List;

@Getter
@AllArgsConstructor
public class BracketResponse {

    private Long id;
    private Long competitionId;
    private BracketType bracketType;
    private int participantCount;
    private List<LeagueMatchResponse> leagueMatches;
    private List<TournamentMatchResponse> tournamentMatches;

    public static BracketResponse fromLeague(Bracket bracket, List<LeagueMatch> matches) {
        return new BracketResponse(
                bracket.getId(),
                bracket.getCompetition().getId(),
                bracket.getBracketType(),
                bracket.getParticipantCount(),
                matches.stream().map(LeagueMatchResponse::from).toList(),
                null
        );
    }

    public static BracketResponse fromTournament(Bracket bracket, List<TournamentMatch> matches) {
        return new BracketResponse(
                bracket.getId(),
                bracket.getCompetition().getId(),
                bracket.getBracketType(),
                bracket.getParticipantCount(),
                null,
                matches.stream().map(TournamentMatchResponse::from).toList()
        );
    }
}
