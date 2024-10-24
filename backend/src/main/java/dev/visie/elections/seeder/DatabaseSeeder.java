package dev.visie.elections.seeder;

import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.service.AuthenticationService;
import dev.visie.elections.service.PartyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class DatabaseSeeder implements CommandLineRunner {

    private final AuthenticationService authenticationService;
    private final PartyService partyService;

    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(AuthenticationService authenticationService, PasswordEncoder passwordEncoder, PartyService partyService) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.partyService = partyService;
    }

    @Override
    public void run(String... args) throws Exception {
        CreateUserDTO userDto = new CreateUserDTO(
                "User",
                "user@user.com",
                "tester",
                RoleEnum.USER
        );

        this.authenticationService.register(userDto, userDto.getRoleName(), true);

        CreateUserDTO adminDto = new CreateUserDTO(
                "Admin",
                "admin@admin.com",
                "admin",
                RoleEnum.ADMIN
        );
        this.authenticationService.register(adminDto, adminDto.getRoleName(), true);

        List<PartyLogoDTO> partyLogos = Arrays.asList(
                new PartyLogoDTO("VVD", 1),
                new PartyLogoDTO("D66", 2),
                new PartyLogoDTO("groenlinks", 3),
                new PartyLogoDTO("pvv", 4),
                new PartyLogoDTO("cda", 5),
                new PartyLogoDTO("sp", 6),
                new PartyLogoDTO("fvd", 7),
                new PartyLogoDTO("pvdd", 8),
                new PartyLogoDTO("christenunie", 9),
                new PartyLogoDTO("volt", 10),
                new PartyLogoDTO("ja21", 11),
                new PartyLogoDTO("sgp", 12),
                new PartyLogoDTO("denk", 13),
                new PartyLogoDTO("50plus", 14),
                new PartyLogoDTO("bbb", 15),
                new PartyLogoDTO("bij1", 16),
                new PartyLogoDTO("piraten", 17),
                new PartyLogoDTO("bvnl", 18),
                new PartyLogoDTO("nsc", 19),
                new PartyLogoDTO("splinter", 20),
                new PartyLogoDTO("LP", 21),
                new PartyLogoDTO("lef", 22),
                new PartyLogoDTO("svn", 23),
                new PartyLogoDTO("nlplan", 24),
                new PartyLogoDTO("partijvdsport", 25),
                new PartyLogoDTO("partijvoorbasisinkomen", 26));
        for (PartyLogoDTO logo : partyLogos) {
            partyService.savePartyLogo(logo.getLogo(), logo.getId());
        }
    }
}