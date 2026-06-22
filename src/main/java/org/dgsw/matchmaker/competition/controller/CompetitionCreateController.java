package org.dgsw.matchmaker.competition.controller;

import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateDTO;
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
            @RequestBody CompetitionCreateDTO dto
    ) {
        return competitionService.createCompetition(dto);
    }
}