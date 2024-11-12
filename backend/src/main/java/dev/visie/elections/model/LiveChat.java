package dev.visie.elections.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livechat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiveChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "livechat", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChatMessage> messages = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "topic_id")
    @JsonManagedReference
    private Topic topic;

}
