package dev.visie.elections.service.models;

import dev.visie.elections.dto.candidate.CandidateWithVotes;
import dev.visie.elections.model.enums.AreaEnum;
import dev.visie.elections.repository.VotesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {
    private final VotesRepository votesRepository;

    public CandidateService(VotesRepository votesRepository) {
        this.votesRepository = votesRepository;
    }

    public ResponseEntity<?> getCandidatesWithArea(int partyId, AreaEnum area, String searchInput) {
        List<Object[]> candidates = new ArrayList<>();
        switch(area) {
            case MUNICIPALITY:
                    candidates = votesRepository.getCandidateWithVotesAndMunicipality(partyId, searchInput);
                    break;
            case CONSTITUENCY:
                    candidates = votesRepository.getCandidateWithVotesAndConstituency(partyId, searchInput);
                    break;
                default:
                    break;
        }
        if(candidates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CandidateWithVotes> candidateList = candidates.stream()
                .map(result -> CandidateWithVotes.DTOMapper(result))
                .collect(Collectors.toList());

        return ResponseEntity.ok(candidateList);
    }
}
