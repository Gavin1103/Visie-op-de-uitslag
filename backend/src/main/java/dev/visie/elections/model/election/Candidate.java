package dev.visie.elections.model.election;


import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.visie.elections.model.election.compositeId.CandidateId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Candidate {

    @EmbeddedId
    private CandidateId candidateId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name_prefix")
    private String lastNamePrefix;

    @Column(name = "last_name")
    private String lastName;
    private String initails;
    private String locality;


    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id", insertable = false, updatable = false)
    @JsonBackReference
    private Party party;

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + candidateId +
                ", firstName='" + firstName + '\'' +
                ", lastNamePrefix='" + lastNamePrefix + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + locality + '\'' +
                ", partyId=" + (party != null ? party.getPartyId() : "null") +
                '}';
    }
}
