package nl.hva.ict.se.sm3.utils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CandidateId implements Serializable {
    
    @Column(name = "candidate_id")
    private int candidateId;
    
    @Column(name = "party_id")
    private int partyId;

    // Default constructor
    public CandidateId() {
    }

    // Constructor
    public CandidateId(int candidateId, int partyId) {
        this.candidateId = candidateId;
        this.partyId = partyId;
    }

    // Getters and setters
    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    // Override equals and hashCode
    // Override equals and hashCode for composite key
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
