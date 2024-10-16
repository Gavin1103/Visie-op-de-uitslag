package nl.hva.ict.se.sm3.demo.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import nl.hva.ict.se.sm3.utils.CandidateId;

import javax.persistence.JoinColumn;

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
    @MapsId("party_id") 
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    
    public CandidateId getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(CandidateId candidateId) {
        this.candidateId = candidateId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNamePrefix() {
        return lastNamePrefix;
    }

    public void setLastNamePrefix(String lastNamePrefix) {
        this.lastNamePrefix = lastNamePrefix;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getInitials() {
        return initails;
    }

    public void setInitials(String initials) {
        this.initails = initials;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

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
