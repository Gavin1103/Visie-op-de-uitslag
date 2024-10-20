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
        @JoinColumn(name = "party_id", referencedColumnName = "party_id", insertable = false, updatable = false),
        @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id", insertable = false, updatable = false)
    })
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id", insertable = false, updatable = false)
    private Party party;

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
