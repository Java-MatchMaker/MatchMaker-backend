package org.dgsw.matchmaker.domain.competition.repository;

import org.dgsw.matchmaker.domain.competition.domain.entity.CompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<CompetitionEntity, Long> {

}
