package org.dgsw.matchmaker.bracket.repository;

import org.dgsw.matchmaker.bracket.entity.TournamentMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentMatchRepository extends JpaRepository<TournamentMatch, Long> {

    List<TournamentMatch> findAllByBracket_IdOrderByRoundAscMatchOrderAsc(Long bracketId);

    void deleteAllByBracket_Id(Long bracketId);

    void deleteAllByBracket_Competition_Id(Long competitionId);
}
