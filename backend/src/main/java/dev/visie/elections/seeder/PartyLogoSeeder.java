package dev.visie.elections.seeder;

import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.service.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@Profile({"local", "dev", "prod", "gavin"})
public class PartyLogoSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PartyLogoSeeder.class);

    private final JsonSeeder jsonSeeder;
    private final PartyService partyService;

    public PartyLogoSeeder(JsonSeeder jsonSeeder, PartyService partyService) {
        this.jsonSeeder = jsonSeeder;
        this.partyService = partyService;
    }

    @Override
    public void run(String... args) {
        try {
            List<PartyLogoDTO> partyLogos = jsonSeeder.loadData("party_logos.json", PartyLogoDTO.class);
            for (PartyLogoDTO logoDto : partyLogos) {
                if (partyService.existsByLogo(logoDto.getLogo())) {
                    continue;
                }
                try {
                    partyService.savePartyLogo(logoDto.getLogo(), logoDto.getId());
                } catch (Exception e) {
                    logger.error("Error creating party logo {}: {}", logoDto.getLogo(), e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load party logos data: {}", e.getMessage());
        }
    }
}
