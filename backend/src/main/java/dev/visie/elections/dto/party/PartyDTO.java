package dev.visie.elections.dto.party;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private int amountOfVotes;

    public static PartyDTO customPartyMapperDTO(Object[] party) {

        if (party != null) {

            return PartyDTO.builder()
                    .partyId(((Number) party[0]).intValue())
                    .name((String) party[1])
                    .logo((String) party[2])
                    .amountOfVotes(((Number) party[3]).intValue())
                    .build();
        }
        return null;
    }

    public static List<PartyDTO> customPartiesMapperDTO(List<Object[]> parties) {

        if (parties != null) {

            List<PartyDTO> partyDTOs;

            partyDTOs = parties.stream()
                    .map(result -> PartyDTO.builder()
                            .partyId(((Number) result[0]).intValue())
                            .name((String) result[1])
                            .logo((String) result[2])
                            .amountOfVotes(((Number) result[3]).intValue())
                            .build()
                    )
                    .collect(Collectors.toList());

            return partyDTOs;
        }
        return null;
    }
}