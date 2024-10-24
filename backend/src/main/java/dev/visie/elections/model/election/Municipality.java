package dev.visie.elections.model.election;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Municipality {
    @Id
    @Column(name = "municipality_id")
    private String municipalityId;

    private String name;

     @ManyToOne
    @JoinColumn(name = "constituency_id", nullable = false)
     @JsonIgnore
    private Constituency constituency;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Station> stations = new ArrayList<>();

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


    @Override
public String toString() {
    return "Municipality{" +
            "municipalityId='" + municipalityId + '\'' +
            ", name='" + name + '\'' +
            ", constituency=" + (constituency != null ? constituency.getName() : "null") +
            '}';
}

}
