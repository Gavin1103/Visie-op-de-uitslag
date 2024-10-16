package dev.visie.elections.service;

import dev.visie.elections.dto.party.PartyPageResponse;
import dev.visie.elections.model.election.Party;
import dev.visie.elections.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public List<Party> getParties(){
        return partyRepository.findAll();
    }

    public void savePartyLogo(String logo, int id){
        Party party = partyRepository.findById(id).orElseThrow(() -> new RuntimeException("no id found"));;

        party.setLogo(logo);
        partyRepository.save(party);
    }

    public PartyPageResponse getPartyPageInfo(int id){
        PartyPageResponse party = partyRepository.getPartyPageInfo(id);

        

    }
}
