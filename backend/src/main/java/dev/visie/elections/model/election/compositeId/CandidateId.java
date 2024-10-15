package dev.visie.elections.model.election.compositeId;


import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CandidateId implements Serializable {

    @Column(name = "party_id")  // Make sure the column name matches
    private int partyId;

    @Column(name = "candidate_id")  // Make sure the column name matches
    private int candidateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateId)) return false;
        CandidateId that = (CandidateId) o;
        return candidateId == that.candidateId && partyId == that.partyId;
    }

    @Override
    public int hashCode() {
        int result = candidateId;
        result = 31 * result + partyId;
        return result;
    }
}
