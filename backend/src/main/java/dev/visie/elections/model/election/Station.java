package dev.visie.elections.model.election;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Votes> votes = new ArrayList<>();

    public Station(String stationId, String name, String zipcode){
        this.stationId = stationId;
        this.name = name;
        this.zipcode = zipcode;
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
