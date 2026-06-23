package org.dgsw.matchmaker.bracket.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BracketUpdateRequest {

    private List<Long> participantIds;
}
