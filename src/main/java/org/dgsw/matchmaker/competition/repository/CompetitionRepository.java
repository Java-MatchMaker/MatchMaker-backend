package org.dgsw.matchmaker.competition.repository;

import org.dgsw.matchmaker.competition.entity.CompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<CompetitionEntity, Long> {

}
