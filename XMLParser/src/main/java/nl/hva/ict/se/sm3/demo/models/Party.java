package nl.hva.ict.se.sm3.demo.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Party {

    @Id
    @Column(name = "party_id")
    private int partyId;

    private String name;

    private String logo;

    
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<Candidate> candidates = new ArrayList<>();

    // Getters and Setters
    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public String toString() {
        StringBuilder candidatesList = new StringBuilder();
        if (candidates != null) {
            for (Candidate candidate : candidates) {
                candidatesList.append(candidate.toString()).append("\n");
            }
        }
        return "Party{" +
                "partyId=" + partyId +
                ", name='" + name + '\'' +
                ", candidates=\n" + candidatesList +
                '}';
    }
}
