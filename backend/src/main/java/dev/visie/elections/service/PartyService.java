package dev.visie.elections.service;

import dev.visie.elections.dto.party.PartyDTO;
import dev.visie.elections.dto.candidate.CandidateWithVotes;
import dev.visie.elections.dto.party.PartyPageResponse;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.repository.PartyRepository;
import dev.visie.elections.repository.VotesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final VotesRepository votesRepository;

    private final VotesRepository votesRepository;

    private final VotesService votesService;

    public PartyService(PartyRepository partyRepository, VotesRepository votesRepository, VotesService votesService) {
        this.partyRepository = partyRepository;
        this.votesRepository = votesRepository;
        this.votesService = votesService;
    }

    public List<Party> getParties() {
        return partyRepository.findAll();
    }

    public void savePartyLogo(String logo, int id) {
        Party party = partyRepository.findById(id).orElseThrow(() -> new RuntimeException("no id found"));
        ;

        party.setLogo(logo);
        partyRepository.save(party);
    }

    public ResponseEntity<?> getElectedParty() {

        List<Object[]> results = votesRepository.getPartiesOrderedByVotes();

        if (results != null) {

            PartyDTO electedParty = PartyDTO.customPartyMapperDTO(results.get(0), votesService);
            return new ResponseEntity<>(electedParty, HttpStatus.OK);
        }
        return new ResponseEntity<>("Parties not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getPartiesWithStatistics() {

        List<Object[]> results = votesRepository.getPartiesOrderedByVotes();

        if (results != null && !results.isEmpty()) {

            List<PartyDTO> partyDTOs = results.stream()
                    .map(result -> PartyDTO.customPartyMapperDTO(result, votesService))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(partyDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("Parties not found", HttpStatus.NOT_FOUND);
    }

    public PartyPageResponse getPartyPageInfo(int id){
        PartyPageResponse party = partyRepository.getPartyPageInfo(id);
        List<CandidateWithVotes> candidates = votesRepository.getCandidateWithVotes(id);
        party.setCandidates(candidates);
        return party;
    }
}
