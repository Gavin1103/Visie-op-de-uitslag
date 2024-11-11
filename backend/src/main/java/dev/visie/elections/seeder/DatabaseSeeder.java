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
                new CreateTopicDto(1,"Ik wil kaas", "Ik ben ook een klant", user1.getId()),
                new CreateTopicDto(2,"Ik hou van pizza", "Dit is mijn favoriete eten", user1.getId()),
                new CreateTopicDto(3,"Technologie en ik", "Laten we praten over technologie", user1.getId())
        );

        for (CreateTopicDto topicDto : createTopicDtos) {
            topicService.createTopic(topicDto);
        }
    }
}