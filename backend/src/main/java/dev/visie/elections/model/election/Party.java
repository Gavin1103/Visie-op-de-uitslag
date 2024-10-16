package dev.visie.elections.model.election;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Party {

    @Id
    @Column(name = "party_id")
    private int partyId;

    private String name;

    private String logo;

    
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<Candidate> candidates = new ArrayList<>();

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
