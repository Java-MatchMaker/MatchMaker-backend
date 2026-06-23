package org.dgsw.matchmaker.bracket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dgsw.matchmaker.bracket.entity.TournamentMatch;
import org.dgsw.matchmaker.bracket.enums.TournamentRound;

@Getter
@AllArgsConstructor
public class TournamentMatchResponse {

    private Long id;
    private TournamentRound round;
    private int matchOrder;
    private ParticipantSummaryResponse homeParticipant;
    private ParticipantSummaryResponse awayParticipant;
    private ParticipantSummaryResponse winner;

    public static TournamentMatchResponse from(TournamentMatch match) {
        return new TournamentMatchResponse(
                match.getId(),
                match.getRound(),
                match.getMatchOrder(),
                ParticipantSummaryResponse.from(match.getHomeParticipant()),
                ParticipantSummaryResponse.from(match.getAwayParticipant()),
                ParticipantSummaryResponse.from(match.getWinner())
        );
    }
}
