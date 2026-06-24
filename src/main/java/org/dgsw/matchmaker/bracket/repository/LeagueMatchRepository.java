package org.dgsw.matchmaker.bracket.repository;

import org.dgsw.matchmaker.bracket.entity.LeagueMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueMatchRepository extends JpaRepository<LeagueMatch, Long> {

    List<LeagueMatch> findAllByBracket_IdOrderByMatchNumberAsc(Long bracketId);

    void deleteAllByBracket_Id(Long bracketId);

    void deleteAllByBracket_Competition_Id(Long competitionId);
}
