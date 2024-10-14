package dev.visie.elections.controller;

import dev.visie.elections.service.PartyService;
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
    public String getParties() {
        return partyService.getParties();
    }
}
