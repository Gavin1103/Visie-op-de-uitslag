package dev.visie.elections.dto;

import dev.visie.elections.model.election.Constituency;
import dev.visie.elections.model.election.Municipality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AreaDTO {
    private List<Municipality> municipalities;
    private List<Constituency> constituencies;
}
