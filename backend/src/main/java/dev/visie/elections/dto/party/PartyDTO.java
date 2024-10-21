package dev.visie.elections.dto.party;

import jakarta.validation.constraints.NotBlank;

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

    @NotBlank(message = REQUIRED)
    private String name;

    @NotBlank(message = REQUIRED)
    private String logo;

    private int amountOfVotes;

    public static PartyDTO partyMapperDTO(Object[] party) {

        if (party != null) {

            return PartyDTO.builder()
                    .name((String) party[0])
                    .logo((String) party[1])
                    .amountOfVotes(((Number) party[2]).intValue())
                    .build();
        }
        return null;
    }

    public static List<PartyDTO> partiesMapperDTO(List<Object[]> parties) {

        if (parties != null) {

            List<PartyDTO> partyDTOs;

            partyDTOs = parties.stream()
                    .map(result -> PartyDTO.builder()
                            .name((String) result[0])
                            .logo((String) result[1])
                            .amountOfVotes(((Number) result[2]).intValue())
                            .build()
                    )
                    .collect(Collectors.toList());

            return partyDTOs;
        }
        return null;
    }
}