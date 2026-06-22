package org.dgsw.matchmaker.competition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.competition.entity.CompetitionCreateEntity;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.springframework.web.bind.annotation.*;
//고침
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompetitionCreateController {

    private final CompetitionService competitionService;

    @PostMapping("/competitions")
    public CompetitionCreateEntity createCompetition(
            @Valid @RequestBody CompetitionCreateRequestDTO dto
    ) {
        return competitionService.createCompetition(dto);
    }
}