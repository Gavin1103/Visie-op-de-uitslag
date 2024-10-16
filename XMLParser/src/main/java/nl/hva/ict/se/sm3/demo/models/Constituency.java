package nl.hva.ict.se.sm3.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Constituency {
    @Id
    @Column(name = "constituency_id")
    private int constituencyId;

    private String name;

    @OneToMany(mappedBy = "constituency", cascade = CascadeType.ALL)
    private List<Municipality> municipality = new ArrayList<>();


    public int getConstituency() {
        return constituencyId;
    }

    public void setConstituencyId(int constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Municipality> getMunicipality() {
        return municipality;
    }

    public void setMunicipality(List<Municipality> municipality) {
        this.municipality = municipality;
    }

    @Override
public String toString() {
    return "Constituency{" +
            "constituencyId=" + constituencyId +
            ", name='" + name + '\'' +
            ", municipalities=" + municipality.size() + 
            '}';
}
}
