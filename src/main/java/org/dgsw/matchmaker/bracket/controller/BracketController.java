package org.dgsw.matchmaker.bracket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.bracket.dto.BracketCreateRequest;
import org.dgsw.matchmaker.bracket.dto.BracketResponse;
import org.dgsw.matchmaker.bracket.dto.BracketTournamentMatchPatchRequest;
import org.dgsw.matchmaker.bracket.dto.BracketUpdateRequest;
import org.dgsw.matchmaker.bracket.dto.TournamentMatchResponse;
import org.dgsw.matchmaker.bracket.service.BracketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brackets")
public class BracketController {

    private final BracketService bracketService;

    @PostMapping
    public ResponseEntity<BracketResponse> createBracket(@Valid @RequestBody BracketCreateRequest request) {
        return ResponseEntity.ok(bracketService.createBracket(request));
    }

    @GetMapping
    public ResponseEntity<List<BracketResponse>> getBrackets(
            @RequestParam(required = false) Long competitionId
    ) {
        return ResponseEntity.ok(bracketService.getBrackets(competitionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BracketResponse> getBracket(@PathVariable Long id) {
        return ResponseEntity.ok(bracketService.getBracket(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BracketResponse> updateBracket(
            @PathVariable Long id,
            @RequestBody BracketUpdateRequest request
    ) {
        return ResponseEntity.ok(bracketService.updateBracket(id, request));
    }

    @PatchMapping("/tournament-matches/{matchId}")
    public ResponseEntity<TournamentMatchResponse> patchTournamentMatch(
            @PathVariable Long matchId,
            @Valid @RequestBody BracketTournamentMatchPatchRequest request
    ) {
        return ResponseEntity.ok(bracketService.patchTournamentMatch(matchId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBracket(@PathVariable Long id) {
        bracketService.deleteBracket(id);
        return ResponseEntity.noContent().build();
    }
}
