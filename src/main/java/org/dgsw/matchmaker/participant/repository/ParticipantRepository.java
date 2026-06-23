package org.dgsw.matchmaker.participant.repository;

import org.dgsw.matchmaker.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByCompetition_IdOrderByIdAsc(Long competitionId);

    List<Participant> findAllByCompetition_IdAndIdInOrderByIdAsc(Long competitionId, List<Long> ids);

    void deleteAllByCompetition_Id(Long competitionId);
}
