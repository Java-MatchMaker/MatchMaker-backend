package org.dgsw.matchmaker.competition.repository;

import org.dgsw.matchmaker.competition.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
