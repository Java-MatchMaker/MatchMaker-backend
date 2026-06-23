package org.dgsw.matchmaker.domain.competition.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.domain.competition.dto.CompetitionCreateRequestDTO;
import org.dgsw.matchmaker.domain.competition.dto.CompetitionCreateResponseDTO;
import org.dgsw.matchmaker.domain.competition.domain.entity.CompetitionEntity;
import org.dgsw.matchmaker.domain.competition.service.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompetitionCreateController {

    private final CompetitionService competitionService;

    @PostMapping("/competitions")
    public ResponseEntity<CompetitionCreateResponseDTO> createCompetition(
            @RequestBody CompetitionCreateRequestDTO dto
    ) {
        CompetitionEntity response = competitionService.createCompetition(dto);
        return ResponseEntity.ok(CompetitionCreateResponseDTO.to(response));
    }
}