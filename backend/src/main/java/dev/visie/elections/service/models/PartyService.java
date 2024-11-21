package dev.visie.elections.service.models;

import dev.visie.elections.dto.party.PartyDTO;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.repository.PartyRepository;
import dev.visie.elections.repository.VotesRepository;
import dev.visie.elections.service.models.VotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
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
        Party party = partyRepository.findById(id).orElse(null);

        if (party == null) {
            return;
        }

        party.setLogo(logo);
        partyRepository.save(party);
    }

    public ResponseEntity<?> getElectedParty() {

        List<Object[]> results = votesRepository.getPartiesOrderedByVotes();

        if (results != null) {

            PartyDTO electedParty = PartyDTO.customPartyMapperDTO(results.get(0), votesService, null);
            return new ResponseEntity<>(electedParty, HttpStatus.OK);
        }
        return new ResponseEntity<>("Parties not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getPartiesWithStatistics() {

        List<Object[]> results = votesRepository.getPartiesOrderedByVotes();
        if (results != null && !results.isEmpty()) {

            List<PartyDTO> partyDTOs = results.stream()
                    .map(result -> PartyDTO.customPartyMapperDTO(result, votesService, null))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(partyDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("Parties not found", HttpStatus.NOT_FOUND);
    }

    public PartyDTO getPartyPageInfo(int id){
        List<Object[]> party = partyRepository.getPartyPageInfo(id);
        List<Object []> candidates = votesRepository.getCandidateWithVotes(id);
        return PartyDTO.customPartyMapperDTO(party.get(0), votesService, candidates);


    }
}
