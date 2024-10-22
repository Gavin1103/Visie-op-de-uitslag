package dev.visie.elections.controller;

import dev.visie.elections.dto.party.PartyDTO;
import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.dto.votes.TotalAmountOfVotesDTO;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.service.PartyService;
import dev.visie.elections.service.VotesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("election")
public class ElectionController {
    private final PartyService partyService;
    private final VotesService votesService;

    public ElectionController(PartyService partyService, VotesService votesService) {
        this.partyService = partyService;
        this.votesService = votesService;
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

    @GetMapping("parties/votes")
    @Operation(summary = "get all parties ordered by votes (DESC) and with seats")
    public ResponseEntity<?> getPartiesWithStatistics() {
        return partyService.getPartiesWithStatistics();
    }

    @GetMapping("/electedParty")
    @Operation(summary = "Get the elected party")
    public ResponseEntity<?> getElectedParty() {
        return partyService.getElectedParty();
    }

    @GetMapping("/totalAmountOfVotes")
    @Operation(summary = "Get the total amount of votes and electoral quota")
    public TotalAmountOfVotesDTO getTotalAmountOfVotes() {
        return votesService.getTotalAmountOfVotes();
    }

    @GetMapping("party/{id}")
    @Operation(summary = "get a party for the party page by the id")
    public PartyDTO getPartyById(@PathVariable int id) {
        return partyService.getPartyPageInfo(id);
    }
}
