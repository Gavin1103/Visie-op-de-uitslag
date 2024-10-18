package dev.visie.elections.service;

import dev.visie.elections.dto.votes.TotalAmountOfVotesDTO;
import dev.visie.elections.repository.VotesRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class VotesService {
    private final VotesRepository votesRepository;

    @Autowired
    public VotesService(VotesRepository votesRepository) {
        this.votesRepository = votesRepository;
    }

    public ResponseEntity<?> getTotalAmountOfVotes() {
        int totalAmountOfVotes = votesRepository.getTotalAmountOfVotes();
        if (totalAmountOfVotes != 0) {

            double electoralQuota = (double) totalAmountOfVotes / 150;
            int roundedElectoralQuota = (int) Math.round(electoralQuota);

            return new ResponseEntity<>(TotalAmountOfVotesDTO.builder()
                    .totalAmountOfVotes(totalAmountOfVotes)
                    .electoralQuota(roundedElectoralQuota)
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No votes found", HttpStatus.NOT_FOUND);
    }
}