package org.dgsw.matchmaker.participant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.request.UpdateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.dgsw.matchmaker.participant.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/participants")
    public ResponseEntity<ParticipantResponse> createParticipant(
            @Valid @RequestBody CreateParticipantRequest request
    ) {
        return ResponseEntity.ok(participantService.createParticipant(request));
    }

    @GetMapping("/participants/{participantId}")
    public ResponseEntity<ParticipantResponse> getParticipant(
            @PathVariable Long participantId
    ) {
        return ResponseEntity.ok(participantService.getParticipant(participantId));
    }

    @GetMapping("/competitions/{competitionId}/participants")
    public ResponseEntity<List<ParticipantResponse>> getParticipantsByCompetition(
            @PathVariable Long competitionId
    ) {
        return ResponseEntity.ok(
                participantService.getParticipantsByCompetition(competitionId)
        );
    }

    @DeleteMapping("/competitions/{competitionId}/participants/{participantId}")
    public ResponseEntity<String> deleteParticipant(
            @PathVariable Long competitionId,
            @PathVariable Long participantId
    ) {
        participantService.deleteParticipant(competitionId, participantId);
        return ResponseEntity.ok("참가자가 삭제되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponse> updateParticipant(
            @PathVariable Long id,
            @Valid @RequestBody UpdateParticipantRequest request
    ) {
        return ResponseEntity.ok(participantService.updateParticipant(id, request));
    }
}
