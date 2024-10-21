package dev.visie.elections.dto.votes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TotalAmountOfVotesDTO {
    int totalAmountOfVotes;
    int electoralQuota;
}