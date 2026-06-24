package org.dgsw.matchmaker.bracket.service;

import org.dgsw.matchmaker.bracket.entity.Bracket;
import org.dgsw.matchmaker.bracket.entity.LeagueMatch;
import org.dgsw.matchmaker.bracket.entity.TournamentMatch;
import org.dgsw.matchmaker.bracket.enums.BracketType;
import org.dgsw.matchmaker.bracket.enums.TournamentRound;
import org.dgsw.matchmaker.participant.entity.Participant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Component
public class BracketGenerator {

    private static final int MIN_PARTICIPANTS = 2;
    private static final int MAX_TOURNAMENT_PARTICIPANTS = 16;

    public void validateParticipantCount(BracketType bracketType, int participantCount) {
        if (participantCount < MIN_PARTICIPANTS) {
            throw new IllegalArgumentException("참가자는 최소 2명 이상이어야 합니다.");
        }

        if (bracketType == BracketType.TOURNAMENT && participantCount > MAX_TOURNAMENT_PARTICIPANTS) {
            throw new IllegalArgumentException("토너먼트는 최대 16명까지 지원합니다.");
        }
    }

    public List<LeagueMatch> generateLeagueMatches(Bracket bracket, List<Participant> participants) {
        validateParticipantCount(BracketType.LEAGUE, participants.size());

        List<LeagueMatch> matches = new ArrayList<>();
        int matchNumber = 1;

        for (int i = 0; i < participants.size(); i++) {
            for (int j = i + 1; j < participants.size(); j++) {
                matches.add(LeagueMatch.create(
                        bracket,
                        matchNumber++,
                        participants.get(i),
                        participants.get(j)
                ));
            }
        }

        return matches;
    }

    public List<TournamentMatch> generateTournamentMatches(Bracket bracket, List<Participant> participants) {
        validateParticipantCount(BracketType.TOURNAMENT, participants.size());

        int bracketSize = resolveBracketSize(participants.size());
        TournamentRound firstRound = firstRoundForBracketSize(bracketSize);
        List<TournamentRound> rounds = roundsFrom(firstRound);

        List<TournamentMatch> matches = new ArrayList<>();
        for (TournamentRound round : rounds) {
            int matchCount = matchCountForRound(round);
            for (int order = 1; order <= matchCount; order++) {
                matches.add(TournamentMatch.create(bracket, round, order));
            }
        }

        assignParticipantsToFirstRound(matches, firstRound, participants, bracketSize);
        return matches;
    }

    private int resolveBracketSize(int participantCount) {
        int size = MIN_PARTICIPANTS;
        while (size < participantCount) {
            size *= 2;
        }
        return Math.min(size, MAX_TOURNAMENT_PARTICIPANTS);
    }

    private TournamentRound firstRoundForBracketSize(int bracketSize) {
        return switch (bracketSize) {
            case 16 -> TournamentRound.ROUND_OF_16;
            case 8 -> TournamentRound.QUARTER_FINAL;
            case 4 -> TournamentRound.SEMI_FINAL;
            case 2 -> TournamentRound.FINAL;
            default -> throw new IllegalArgumentException("지원하지 않는 토너먼트 인원입니다.");
        };
    }

    private List<TournamentRound> roundsFrom(TournamentRound firstRound) {
        List<TournamentRound> rounds = new ArrayList<>();
        for (TournamentRound round : EnumSet.allOf(TournamentRound.class)) {
            if (round.ordinal() >= firstRound.ordinal()) {
                rounds.add(round);
            }
        }
        return rounds;
    }

    private int matchCountForRound(TournamentRound round) {
        return switch (round) {
            case ROUND_OF_16 -> 8;
            case QUARTER_FINAL -> 4;
            case SEMI_FINAL -> 2;
            case FINAL -> 1;
        };
    }

    private void assignParticipantsToFirstRound(
            List<TournamentMatch> matches,
            TournamentRound firstRound,
            List<Participant> participants,
            int bracketSize
    ) {
        List<TournamentMatch> firstRoundMatches = matches.stream()
                .filter(match -> match.getRound() == firstRound)
                .toList();

        int slotCount = bracketSize;
        for (int slot = 0; slot < slotCount; slot++) {
            int matchIndex = slot / 2;
            boolean homeSlot = slot % 2 == 0;
            Participant participant = slot < participants.size() ? participants.get(slot) : null;

            if (participant == null) {
                continue;
            }

            TournamentMatch match = firstRoundMatches.get(matchIndex);
            if (homeSlot) {
                match.assignHomeParticipant(participant);
            } else {
                match.assignAwayParticipant(participant);
            }
        }
    }
}
