package org.dgsw.matchmaker.bracket.enums;

import org.dgsw.matchmaker.competition.enums.CompetitionType;

public enum BracketType {
    LEAGUE,
    TOURNAMENT;

    public static BracketType from(CompetitionType competitionType) {
        return BracketType.valueOf(competitionType.name());
    }
}
