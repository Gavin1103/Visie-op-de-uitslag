package dev.visie.elections.service.models;

import dev.visie.elections.dto.votes.TotalAmountOfVotesDTO;
import dev.visie.elections.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Object[]> getTotalVotesByPartyForConstituencies(List<String> constituencies) {
        return votesRepository.getTotalVotesByPartyForConstituencies(constituencies);
    }

    // Map to hold the mapping of constituencies to provinces
    private static final Map<String, String> kieskringenToProvinceMap = new HashMap<>();

    static {
        // Initialize the map with the provided kieskringen data
        kieskringenToProvinceMap.put("Groningen", "Groningen");
        kieskringenToProvinceMap.put("Leeuwarden", "Friesland");
        kieskringenToProvinceMap.put("Assen", "Drenthe");
        kieskringenToProvinceMap.put("Zwolle", "Overijssel");
        kieskringenToProvinceMap.put("Lelystad", "Flevoland");
        kieskringenToProvinceMap.put("Nijmegen", "Gelderland");
        kieskringenToProvinceMap.put("Arnhem", "Gelderland");
        kieskringenToProvinceMap.put("Utrecht", "Utrecht");
        kieskringenToProvinceMap.put("Amsterdam", "Noord-Holland");
        kieskringenToProvinceMap.put("Haarlem", "Noord-Holland");
        kieskringenToProvinceMap.put("Den Helder", "Noord-Holland");
        kieskringenToProvinceMap.put("'s-Gravenhage", "Zuid-Holland");
        kieskringenToProvinceMap.put("Rotterdam", "Zuid-Holland");
        kieskringenToProvinceMap.put("Dordrecht", "Zuid-Holland");
        kieskringenToProvinceMap.put("Leiden", "Zuid-Holland");
        kieskringenToProvinceMap.put("Middelburg", "Zeeland");
        kieskringenToProvinceMap.put("Tilburg", "Noord-Brabant");
        kieskringenToProvinceMap.put("'s-Hertogenbosch", "Noord-Brabant");
        kieskringenToProvinceMap.put("Maastricht", "Limburg");
        kieskringenToProvinceMap.put("Bonaire", "Caribbean Netherlands");
    }

    // Helper method to get the province name from the constituency using the map
    private String getProvinceNameForConstituency(String constituencyName) {
        return kieskringenToProvinceMap.getOrDefault(constituencyName, "Unknown");
    }

    public Map<String, String> getWinnersByProvince() {
        // Fetch the total votes by party for each constituency
        List<Object[]> results = votesRepository.getTotalVotesByPartyForProvinces();

        // If results are null or empty, return an empty map
        if (results == null || results.isEmpty()) {
            return new HashMap<>();
        }

        // Map to accumulate the total votes per party per province
        Map<String, Map<String, Long>> provinceVotes = new HashMap<>(); // Use Long for vote amounts

        // Process each result from the query
        for (Object[] result : results) {
            String partyName = (String) result[1];
            String constituencyName = (String) result[2];
            Long voteAmount = (Long) result[3]; // Change to Long

            // Get the province name based on the constituency
            String provinceName = getProvinceNameForConstituency(constituencyName);

            // Initialize province map if it doesn't exist
            provinceVotes.putIfAbsent(provinceName, new HashMap<>());

            // Accumulate votes for the party in the province
            provinceVotes.get(provinceName).merge(partyName, voteAmount, Long::sum); // Use Long for summing
        }

        // Map to store the winner for each province
        Map<String, String> winners = new HashMap<>();

        // Identify the winning party for each province
        for (Map.Entry<String, Map<String, Long>> entry : provinceVotes.entrySet()) {
            String province = entry.getKey();
            Map<String, Long> partyVotes = entry.getValue(); // Use Long for vote amounts

            // Find the party with the maximum votes in the province
            String winner = partyVotes.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            winners.put(province, winner);
        }

        // Return the map with province names as keys and winner party names as values
        return winners;
    }

}
