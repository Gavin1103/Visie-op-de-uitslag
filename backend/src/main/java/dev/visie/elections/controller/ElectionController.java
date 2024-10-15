package dev.visie.elections.controller;

import dev.visie.elections.model.election.Party;
import dev.visie.elections.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
