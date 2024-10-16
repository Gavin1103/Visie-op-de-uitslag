package nl.hva.ict.se.sm3.demo.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import nl.hva.ict.se.sm3.utils.VotesId;

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
    @JoinColumn(name = "station_id", referencedColumnName = "station_id", insertable = false, updatable = false)
    private Station station;

    public VotesId getVotesId() {
        return votesId;
    }

    public void setVotesId(VotesId votesId) {
        this.votesId = votesId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
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
