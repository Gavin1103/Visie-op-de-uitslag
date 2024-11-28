package dev.visie.elections.seeder;

import dev.visie.elections.dto.party.PartyLogoDTO;
import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.service.AuthenticationService;
import dev.visie.elections.service.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class DatabaseSeeder implements CommandLineRunner {

    private final AuthenticationService authenticationService;
    private final PartyService partyService;
    private final TopicService topicService;
    private final UserService userService;
    private final TopicRatingService topicRatingService;
    private final AnswerRatingService answerRatingService;
    private final CommentRatingService commentRatingService;

    public DatabaseSeeder(AuthenticationService authenticationService,
                          PartyService partyService,
                          TopicService topicService,
                          UserService userService,
                          TopicRatingService topicRatingService,
                          AnswerRatingService answerRatingService,
                          CommentRatingService commentRatingService
    ) {
        this.authenticationService = authenticationService;
        this.partyService = partyService;
        this.topicService = topicService;
        this.userService = userService;
        this.topicRatingService = topicRatingService;
        this.answerRatingService = answerRatingService;
        this.commentRatingService = commentRatingService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.createUsers();
        this.createPartyLogos();
        this.createTopics();
        this.createTopicRatings();
    }

    private void createUsers() {
        List<CreateUserDTO> users = List.of(
                new CreateUserDTO("Admin", "admin@admin.com", "admin", RoleEnum.ADMIN),
                new CreateUserDTO("Gavin", "gavin@admin.com", "gavin", RoleEnum.ADMIN),
                new CreateUserDTO("Esa", "esa@admin.com", "esa", RoleEnum.ADMIN),
                new CreateUserDTO("Jasper", "jasper@admin.com", "jasper", RoleEnum.ADMIN),
                new CreateUserDTO("Aaron", "aaron@admin.com", "aaron", RoleEnum.ADMIN)
        );

        for (CreateUserDTO userDto : users) {
            this.authenticationService.register(userDto, userDto.getRoleName(), true);
        }
    }

    private void createPartyLogos() {
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

    private void createTopics() {
        User testUser = userService.getUserByEmail("admin@admin.com");

        List<CreateTopicDto> createTopicDtos = Arrays.asList(
                new CreateTopicDto("Ik wil kaas", "Ik ben ook een klant"),
                new CreateTopicDto("Ik hou van pizza", "Dit is mijn favoriete eten"),
                new CreateTopicDto("Technologie en ik", "Laten we praten over technologie"),
                new CreateTopicDto("Reizen in Europa", "Wat zijn de beste plekken om te bezoeken?"),
                new CreateTopicDto("Sporten is gezond", "Ik probeer dagelijks te sporten"),
                new CreateTopicDto("Favoriete films", "Wat is jouw favoriete film?"),
                new CreateTopicDto("Leer een nieuwe taal", "Waarom zou je een nieuwe taal leren?"),
                new CreateTopicDto("Boeken lezen", "Ik hou van lezen"),
                new CreateTopicDto("Favoriete seizoen", "Wat is jouw favoriete seizoen?"),
                new CreateTopicDto("Favoriete dier", "Welk dier vind je het leukst?"),
                new CreateTopicDto("Console vs PC", "Liever console of PC"),
                new CreateTopicDto("Mouse and keyboard vs controller", "I think that aim assist is overpowerd"),
                new CreateTopicDto("Beste game van het jaar", "Wat vind jij?"),
                new CreateTopicDto("Wereldreis maken", "Een droom voor velen"),
                new CreateTopicDto("Fietsen vs autorijden", "Wat is beter voor het milieu?"),
                new CreateTopicDto("Favoriete eten", "Wat eet je het liefst?"),
                new CreateTopicDto("Katten of honden", "Wat is jouw favoriete huisdier?"),
                new CreateTopicDto("Studeren in het buitenland", "Wat zijn de voordelen?"),
                new CreateTopicDto("Fitness doelen", "Wat zijn jouw doelen voor dit jaar?"),
                new CreateTopicDto("Zonsondergangen", "De mooiste zonsondergang die ik heb gezien was in...")
        );

        for (CreateTopicDto topicDto : createTopicDtos) {
            topicService.createTopic(topicDto, testUser.getEmail());
        }
    }

    private void createTopicRatings() {

        Topic topic = topicService.getTopicById(1L);
        Topic topic2 = topicService.getTopicById(2L);
        Topic topic3 = topicService.getTopicById(3L);

        RatingDTO ratingDtoLike = new RatingDTO(topic.getId(), true);
        RatingDTO ratingDtoDislike = new RatingDTO(topic.getId(), false);

        RatingDTO ratingDtoLike2 = new RatingDTO(topic2.getId(), true);
        RatingDTO ratingDtoDislike2 = new RatingDTO(topic2.getId(), false);

        RatingDTO ratingDtoLike3 = new RatingDTO(topic3.getId(), true);
        RatingDTO ratingDtoDislike3 = new RatingDTO(topic3.getId(), false);

        this.topicRatingService.createOrUpdateRating(ratingDtoLike, "admin@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoLike, "gavin@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoLike, "esa@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike, "jasper@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike, "aaron@admin.com");

        this.topicRatingService.createOrUpdateRating(ratingDtoLike2, "admin@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoLike2, "gavin@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoLike2, "esa@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoLike2, "jasper@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike2, "aaron@admin.com");

        this.topicRatingService.createOrUpdateRating(ratingDtoLike3, "admin@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike3, "gavin@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike3, "esa@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike3, "jasper@admin.com");
        this.topicRatingService.createOrUpdateRating(ratingDtoDislike3, "aaron@admin.com");
    }

    private void createAnswerRatings(){}

    private void createCommentRatings(){}

}