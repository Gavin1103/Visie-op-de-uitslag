package nl.hva.ict.se.sm3.utils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
public class VotesId implements Serializable {

    @Embedded
    private CandidateId candidateId;  // Reusing CandidateId for partyId and candidateId
    
    @Column(name = "station_id")
    private String stationId;  // New field for stationId

    // Default constructor
    public VotesId() {}

    // Getters, setters, equals, and hashCode methods
    public CandidateId getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(CandidateId candidateId) {
        this.candidateId = candidateId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

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

