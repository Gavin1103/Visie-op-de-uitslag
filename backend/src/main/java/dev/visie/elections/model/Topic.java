package dev.visie.elections.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import dev.visie.elections.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends BaseModel {

    private String statement;

    @Column(length = 1000)
    private String message;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    private List<Answer> answers;
}
