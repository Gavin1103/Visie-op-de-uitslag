package dev.visie.elections.service;

import dev.visie.elections.dto.party.PartyDTO;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.repository.PartyRepository;
import dev.visie.elections.repository.VotesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    private final VotesRepository votesRepository;

    public PartyService(PartyRepository partyRepository, VotesRepository votesRepository) {
        this.partyRepository = partyRepository;
        this.votesRepository = votesRepository;
    }

    public List<Party> getParties(){
        return partyRepository.findAll();
    }

    public void savePartyLogo(String logo, int id){
        Party party = partyRepository.findById(id).orElseThrow(() -> new RuntimeException("no id found"));;

        party.setLogo(logo);
        partyRepository.save(party);
    }

    public ResponseEntity<?> getPartyWithTheMostVotes() {

        List<Object[]> results = votesRepository.getPartiesOrderedByVotes();

        if (results != null) {
            PartyDTO electedParty = PartyDTO.partyMapperDTO(results.get(0));
            return new ResponseEntity<>(electedParty, HttpStatus.OK);
        }
        return new ResponseEntity<>("Parties not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getPartiesWithVotes() {

        List<Object[]> results = votesRepository.getPartiesOrderedByVotes();

        if (results != null) {
            List<PartyDTO> partyDTOs =  PartyDTO.partiesMapperDTO(results);
            return new ResponseEntity<>(partyDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("Parties not found", HttpStatus.NOT_FOUND);
    }
}
