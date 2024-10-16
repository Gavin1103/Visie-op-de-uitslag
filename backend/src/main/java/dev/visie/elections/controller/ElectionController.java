package dev.visie.elections.controller;

import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.dto.party.PartyPageResponse;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("election")
public class ElectionController {
    private final PartyService partyService;

    public ElectionController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping("parties")
    @Operation(summary = "getting all the parties")
    public List<Party> getParties() {
        return partyService.getParties();
    }

    @PostMapping("set-partylogo")
    @Operation(summary = "set the logo if the party in db")
    public void setPartyLogo(@RequestBody PartyLogoDTO partyLogo) {
        partyService.savePartyLogo(partyLogo.getLogo(), partyLogo.getId());
    }

    @GetMapping("party/{id}")
    @Operation(summary = "get a party for the party page by the id")
    public PartyPageResponse getPartyById(@PathVariable int id) {
        return partyService.getPartyPageInfo(id);
    }
}
