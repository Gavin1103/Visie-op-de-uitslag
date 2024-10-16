package dev.visie.elections.dto.party;

import dev.visie.elections.dto.candidate.CandidateWithVotes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PartyPageResponse {
    private int partyId;
    private String name;
    private String logo;
    private List<CandidateWithVotes> candidates;
    private Long totalVotes;
    private int totalSeats;

    public PartyPageResponse(int partyId, String name, String logo, Long totalVotes) {
        this.partyId = partyId;
        this.name = name;
        this.logo = logo;
        this.totalVotes = totalVotes;
    }
}
