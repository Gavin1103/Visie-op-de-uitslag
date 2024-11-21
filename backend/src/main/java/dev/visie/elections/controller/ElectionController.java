package dev.visie.elections.controller;

import dev.visie.elections.dto.AreaDTO;
import dev.visie.elections.dto.party.PartyDTO;
import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.dto.votes.TotalAmountOfVotesDTO;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.model.enums.AreaEnum;
import dev.visie.elections.service.AreaService;
import dev.visie.elections.service.models.CandidateService;
import dev.visie.elections.service.models.PartyService;
import dev.visie.elections.service.models.VotesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("election")
public class ElectionController {
    private final PartyService partyService;
    private final VotesService votesService;
    private final CandidateService candidateService;
    private final AreaService areaService;

    public ElectionController(PartyService partyService, VotesService votesService, CandidateService candidateService, AreaService areaService) {
        this.partyService = partyService;
        this.votesService = votesService;
        this.candidateService = candidateService;
        this.areaService = areaService;
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

    @GetMapping("candidates/{partyId}/{area}/{searchInput}")
    @Operation(summary = "get a list of candidates for a party with the amount of votes they got for a municipality or constituency")
    public ResponseEntity<?> getCandidatesWithArea(@PathVariable int partyId, @PathVariable AreaEnum area, @PathVariable String searchInput) {
        return candidateService.getCandidatesWithArea(partyId, area, searchInput);
    }

    @GetMapping("areas")
    @Operation(summary = "get all the municipalities and constituencies")
    public AreaDTO getElectionAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("totalVotesByParty")
    public ResponseEntity<List<Object[]>> getTotalVotesByPartyForConstituencies(
            @RequestParam("constituencies") List<String> constituencies) {
        List<Object[]> results = votesService.getTotalVotesByPartyForConstituencies(constituencies);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/winners-by-province")
    @Operation(summary = "Get the winning party for each province")
    public ResponseEntity<Map<String, String>> getWinnersByProvince() {
        Map<String, String> winners = votesService.getWinnersByProvince();

        if (winners.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new HashMap<>());
        }

        return ResponseEntity.ok(winners);
    }


}
