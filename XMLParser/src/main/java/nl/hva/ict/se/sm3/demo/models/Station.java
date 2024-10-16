package nl.hva.ict.se.sm3.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Station {
    @Id
    @Column(name = "station_id")
    private String stationId;
    private String name;
    private String zipcode;

     @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;
    

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Votes> votes = new ArrayList<>();

    public Station(String stationId, String name, String zipcode){
        this.stationId = stationId;
        this.name = name;
        this.zipcode = zipcode;
    }

    public Station() {}
  
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<Votes> getVotes() {
        return votes;
    }

    public void setVotes(List<Votes> votes) {
        this.votes = votes;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    @Override
    public String toString() {
        return "Station{" +
            "stationId='" + stationId + '\'' +
            ", name='" + name + '\'' +
            ", zipcode='" + zipcode + '\'' +
            ", municipality=" + (municipality != null ? municipality.getName() : "null") +
            ", votes=" + votes +
            '}';
    }
}
