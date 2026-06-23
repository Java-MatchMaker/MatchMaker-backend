package org.dgsw.matchmaker.bracket.repository;

import org.dgsw.matchmaker.bracket.entity.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BracketRepository extends JpaRepository<Bracket, Long> {

    List<Bracket> findAllByCompetition_IdOrderByIdAsc(Long competitionId);

    void deleteAllByCompetition_Id(Long competitionId);
}
