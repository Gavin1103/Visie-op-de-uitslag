package dev.visie.elections.seeder;

import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.model.User;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.service.AuthenticationService;
import dev.visie.elections.service.PartyService;
import dev.visie.elections.service.TopicService;
import dev.visie.elections.service.UserService;
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
    private final TopicService topicService;

    private final UserService userService;

    public DatabaseSeeder(AuthenticationService authenticationService,
                          PasswordEncoder passwordEncoder,
                          PartyService partyService,
                          TopicService topicService,
                          UserService userService
    ) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.partyService = partyService;
        this.topicService = topicService;
        this.userService = userService;
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

        this.createTopics();
    }

    private void createTopics() {
        User user1 = userService.getUserByEmail("user@user.com");
        User user2 = userService.getUserByEmail("admin@admin.com");

        List<CreateTopicDto> createTopicDtos = Arrays.asList(
                // Topics for user1
                new CreateTopicDto( "Ik wil kaas", "Ik ben ook een klant"),
                new CreateTopicDto("Ik hou van pizza", "Dit is mijn favoriete eten"),
                new CreateTopicDto("Technologie en ik", "Laten we praten over technologie"),
                new CreateTopicDto( "Reizen in Europa", "Wat zijn de beste plekken om te bezoeken?"),
                new CreateTopicDto("Sporten is gezond", "Ik probeer dagelijks te sporten"),
                new CreateTopicDto( "Favoriete films", "Wat is jouw favoriete film?"),
                new CreateTopicDto( "Leer een nieuwe taal", "Waarom zou je een nieuwe taal leren?"),
                new CreateTopicDto( "Boeken lezen", "Ik hou van lezen"),
                new CreateTopicDto( "Favoriete seizoen", "Wat is jouw favoriete seizoen?"),
                new CreateTopicDto( "Favoriete dier", "Welk dier vind je het leukst?"),

                // Topics for user2
                new CreateTopicDto( "Console vs PC", "Liever console of PC"),
                new CreateTopicDto( "Mouse and keyboard vs controller", "I think that aim assist is overpowerd"),
                new CreateTopicDto("Beste game van het jaar", "Wat vind jij?"),
                new CreateTopicDto( "Wereldreis maken", "Een droom voor velen"),
                new CreateTopicDto( "Fietsen vs autorijden", "Wat is beter voor het milieu?"),
                new CreateTopicDto( "Favoriete eten", "Wat eet je het liefst?"),
                new CreateTopicDto( "Katten of honden", "Wat is jouw favoriete huisdier?"),
                new CreateTopicDto("Studeren in het buitenland", "Wat zijn de voordelen?"),
                new CreateTopicDto( "Fitness doelen", "Wat zijn jouw doelen voor dit jaar?"),
                new CreateTopicDto( "Zonsondergangen", "De mooiste zonsondergang die ik heb gezien was in...")
        );

        for (CreateTopicDto topicDto : createTopicDtos) {
            topicService.createTopic(topicDto, user1.getEmail());
        }

    }
}