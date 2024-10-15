package dev.visie.elections.model.election.compositeId;


import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class VotesId implements Serializable {

    @Embedded
    private CandidateId candidateId;  // Reusing CandidateId for partyId and candidateId

    @Column(name = "stationId")  // Same applies to stationId
    private String stationId;  // New field for stationId

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotesId votesId = (VotesId) o;

        if (!stationId.equals(votesId.stationId)) return false;
        return candidateId != null ? candidateId.equals(votesId.candidateId) : votesId.candidateId == null;
    }

    @Override
    public int hashCode() {
        int result = candidateId != null ? candidateId.hashCode() : 0;
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        return result;
    }
    
}

