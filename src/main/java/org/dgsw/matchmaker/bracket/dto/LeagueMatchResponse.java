package org.dgsw.matchmaker.bracket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dgsw.matchmaker.bracket.entity.LeagueMatch;

@Getter
@AllArgsConstructor
public class LeagueMatchResponse {

    private Long id;
    private int matchNumber;
    private ParticipantSummaryResponse homeParticipant;
    private ParticipantSummaryResponse awayParticipant;

    public static LeagueMatchResponse from(LeagueMatch match) {
        return new LeagueMatchResponse(
                match.getId(),
                match.getMatchNumber(),
                ParticipantSummaryResponse.from(match.getHomeParticipant()),
                ParticipantSummaryResponse.from(match.getAwayParticipant())
        );
    }
}
