package org.dgsw.matchmaker.competition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.dgsw.matchmaker.participant.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<CompetitionCreateResponse> createCompetition(
            @Valid @RequestBody CompetitionCreateRequest request
    ) {
        return ResponseEntity.ok(competitionService.createCompetition(request));
    }

    @GetMapping("/{competitionId}/participants")
    public ResponseEntity<List<ParticipantResponse>> getParticipantsByCompetition(
            @PathVariable Long competitionId
    ) {
        return ResponseEntity.ok(
                participantService.getParticipantsByCompetition(competitionId)
        );
    }
}
