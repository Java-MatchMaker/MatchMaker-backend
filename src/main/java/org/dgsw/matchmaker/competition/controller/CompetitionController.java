package org.dgsw.matchmaker.competition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequest;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponse;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
