package org.dgsw.matchmaker.participant.repository;

import org.dgsw.matchmaker.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByCompetition_IdOrderByStudentIdAsc(Long competitionId);
}
