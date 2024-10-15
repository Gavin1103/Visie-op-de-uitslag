package dev.visie.elections.model.election;

import dev.visie.elections.model.election.compositeId.VotesId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Votes {

    @EmbeddedId
    private VotesId votesId;

    private int amount;  

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "partyId", referencedColumnName = "partyId", insertable = false, updatable = false),
        @JoinColumn(name = "candidateId", referencedColumnName = "candidateId", insertable = false, updatable = false)
    })
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "stationId", insertable = false, updatable = false)
    private Station station;

    public void setStation(Station station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Votes{" +
            "votesId=" + votesId +
            ", amount=" + amount +
            ", candidate=" + (candidate != null ? candidate.getLastName() : "null") +
            ", station=" + (station != null ? station.getName() : "null") +
            '}';
    }
}
