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

    public TotalAmountOfVotesDTO getTotalAmountOfVotes() {
        int totalAmountOfVotes = votesRepository.getTotalAmountOfVotes();
        if (totalAmountOfVotes != 0) {

            int electoralQuota = this.calculateElectoralQuota(totalAmountOfVotes);

            return TotalAmountOfVotesDTO.builder()
                    .totalAmountOfVotes(totalAmountOfVotes)
                    .electoralQuota(electoralQuota)
                    .build();
        }
        return null;
    }

    public int calculateAmountOfSeats(int amountOfVotes) {

        int electoralQuota = this.getTotalAmountOfVotes().getElectoralQuota();
        return (int) Math.floor(amountOfVotes / (double) electoralQuota);
    }

    private int calculateElectoralQuota(int totalAmountOfVotes) {

        double electoralQuota = (double) totalAmountOfVotes / 150;
        return (int) Math.round(electoralQuota);
    }

}