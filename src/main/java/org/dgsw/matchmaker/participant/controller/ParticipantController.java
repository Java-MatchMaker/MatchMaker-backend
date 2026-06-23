package org.dgsw.matchmaker.participant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgsw.matchmaker.participant.dto.request.CreateParticipantRequest;
import org.dgsw.matchmaker.participant.dto.response.ParticipantResponse;
import org.dgsw.matchmaker.participant.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ParticipantResponse> createParticipant(
            @Valid @RequestBody CreateParticipantRequest request
    ) {
        return ResponseEntity.ok(participantService.createParticipant(request));
    }
}
