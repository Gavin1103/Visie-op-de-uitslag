package dev.visie.elections.dto.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CandidateWithVotes {
    private int candidateId;
    private int partyId;
    private Long votes;
    private String fullName;
}
