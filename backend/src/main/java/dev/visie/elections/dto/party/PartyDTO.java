package dev.visie.elections.dto.party;

import dev.visie.elections.dto.candidate.CandidateWithVotes;
import dev.visie.elections.service.models.VotesService;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PartyDTO {

    private static final String REQUIRED = "required";

    @NotNull
    private int partyId;

    @NotBlank(message = REQUIRED)
    private String name;

    @NotBlank(message = REQUIRED)
    private String logo;

    private List<CandidateWithVotes> candidates;

    Long amountOfVotes;

    private int amountOfSeats;

    public static PartyDTO customPartyMapperDTO(Object[] party, VotesService votesService, List<Object[]> candidates) {
        List<CandidateWithVotes> candidateList;
        candidateList = Collections.emptyList();
        if (candidates != null) {
            candidateList = candidates.stream()
                    .map(result -> CandidateWithVotes.DTOMapper(result))
                    .collect(Collectors.toList());
        }

        if (party != null) {

            int partyId = ((Number) party[0]).intValue();
            String name = (String) party[1];
            String logo = (String) party[2];

            long amountOfVotes = ((Number) party[3]).longValue();
            int amountOfSeats = votesService.calculateAmountOfSeats((int) amountOfVotes);

            return PartyDTO.builder()
                    .partyId(partyId)
                    .name(name)
                    .logo(logo)
                    .amountOfVotes(amountOfVotes)
                    .amountOfSeats(amountOfSeats)
                    .candidates(candidateList)
                    .build();
        }

        return null;
    }
}