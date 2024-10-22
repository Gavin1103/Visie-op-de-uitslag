package dev.visie.elections.dto.candidate;

import dev.visie.elections.model.election.compositeId.CandidateId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CandidateWithVotes {
    private CandidateId candidateId;
    private String fullName;
    private Long votes;
}
