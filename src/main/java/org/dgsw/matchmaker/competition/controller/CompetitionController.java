package org.dgsw.matchmaker.competition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @PostMapping
    public ResponseEntity<CompetitionCreateResponse> createCompetition(
            @Valid @RequestBody CompetitionCreateRequest request
    ) {
        return ResponseEntity.ok(competitionService.createCompetition(request));
    }

    @PutMapping
    public ResponseEntity<CompetitionCreateResponse> updateCompetition(
            @Valid @RequestBody CompetitionCreateRequest request
    ) {
        log.info("updateCompetition id={}", request.getId());
        return ResponseEntity.ok(competitionService.updateCompetition(request));
    }
}
