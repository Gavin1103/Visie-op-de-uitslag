package dev.visie.elections.dto.candidate;

import dev.visie.elections.model.election.compositeId.CandidateId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CandidateWithVotes {
    private CandidateId candidateId;
    private String fullName;
    private Long votes;

    public static CandidateWithVotes DTOMapper(Object [] candidate) {
        if (candidate != null) {
            CandidateId candidateId = new CandidateId(((Number) candidate[1]).intValue(), ((Number) candidate[0]).intValue());
            String fullName;
            if(candidate[3] != null) {
               fullName = candidate[2] +  " " + candidate[3] + " " + candidate[4];
            }
            else {
                fullName = candidate[2] + " " + candidate[4];
            }
            Long votes = ((Number) candidate[5]).longValue();

            return CandidateWithVotes.builder()
                    .candidateId(candidateId)
                    .fullName(fullName)
                    .votes(votes)
                    .build();
        }

        return null;
    }

    @Override
    public String toString() {
        return "CandidateWithVotes{" +
                "candidateId=" + candidateId +
                ", fullName='" + fullName + '\'' +
                ", votes=" + votes +
                '}';
    }
}
