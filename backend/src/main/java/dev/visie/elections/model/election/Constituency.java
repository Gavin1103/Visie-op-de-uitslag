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
public class Constituency {
    @Id
    @Column(name = "constituency_id")
    private int constituencyId;

    private String name;

    @OneToMany(mappedBy = "constituency", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Municipality> municipality = new ArrayList<>();

    @Override
public String toString() {
    return "Constituency{" +
            "constituencyId=" + constituencyId +
            ", name='" + name + '\'' +
            ", municipalities=" + municipality.size() + 
            '}';
}
}
