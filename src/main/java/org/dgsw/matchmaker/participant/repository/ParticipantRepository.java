package org.dgsw.matchmaker.participant.repository;

import org.dgsw.matchmaker.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findAllByCompetition_IdOrderByStudentIdAsc(Long competitionId);

    List<Participant> findAllByCompetition_IdOrderByIdAsc(Long competitionId);

    List<Participant> findAllByCompetition_IdAndIdInOrderByIdAsc(Long competitionId, List<Long> ids);

    Optional<Participant> findByCompetition_IdAndStudentId(Long competitionId, Integer studentId);

    void deleteAllByCompetition_Id(Long competitionId);
}
