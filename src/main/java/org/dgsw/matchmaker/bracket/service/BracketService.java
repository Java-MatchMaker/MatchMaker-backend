package org.dgsw.matchmaker.bracket.service;

import org.dgsw.matchmaker.bracket.dto.BracketCreateRequest;
import org.dgsw.matchmaker.bracket.dto.BracketResponse;
import org.dgsw.matchmaker.bracket.dto.BracketUpdateRequest;

import java.util.List;

public interface BracketService {

    BracketResponse createBracket(BracketCreateRequest request);

    List<BracketResponse> getBrackets(Long competitionId);

    BracketResponse getBracket(Long id);

    BracketResponse updateBracket(Long id, BracketUpdateRequest request);

    void deleteBracket(Long id);
}
