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
public class Municipality {
    @Id
    @Column(name = "municipality_id")
    private String municipalityId;

    private String name;

     @ManyToOne
    @JoinColumn(name = "constituency_id", nullable = false)
    private Constituency constituency;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    private List<Station> stations = new ArrayList<>();

    public String getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(String municipalityId) {
        int length = municipalityId.length();
        switch (length) {
            case 2:
                municipalityId = "00" + municipalityId;
                break;
            case 3:
                municipalityId = "0" + municipalityId;
                
                break;
            case 1:
                municipalityId = "000" + municipalityId;
                
                break;
        
            default:
                break;
        }
        this.municipalityId = municipalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    @Override
public String toString() {
    return "Municipality{" +
            "municipalityId='" + municipalityId + '\'' +
            ", name='" + name + '\'' +
            ", constituency=" + (constituency != null ? constituency.getName() : "null") +
            '}';
}

}
