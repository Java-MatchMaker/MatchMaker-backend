package org.dgsw.matchmaker.competition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.competition.dto.CompetitionCreateResponseDTO;
import org.dgsw.matchmaker.competition.entity.CompetitionEntity;
import org.dgsw.matchmaker.competition.service.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//고침
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompetitionCreateController {

    private final CompetitionService competitionService;

    @PostMapping("/competitions")
    public ResponseEntity<CompetitionCreateResponseDTO> createCompetition(
            @Valid @RequestBody CompetitionCreateRequestDTO dto
    ) {
        CompetitionEntity response = competitionService.createCompetition(dto);
        return ResponseEntity.ok(CompetitionCreateResponseDTO.to(response));
    }
}