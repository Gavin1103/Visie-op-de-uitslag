package dev.visie.elections;

import dev.visie.elections.controller.TopicController;
import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.GetTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.service.JwtService;
import dev.visie.elections.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TopicControllerTest {

    private TopicController topicController;
    private TopicService topicService;
    private JwtService jwtService;
    private final CreateTopicDto testTopic = new CreateTopicDto("Test statement", "Test message");
    private final TopicResponseDto testTopicResponse = new TopicResponseDto(
            1L,
            "Test statement",
            "Test message",
            "Test user",
            LocalDateTime.now(),
            LocalDateTime.now(),
            new AmountOfRatingsDTO(2, 10),
            50
    );

    @BeforeEach
    void setUp() {
        topicService = Mockito.mock(TopicService.class);
        jwtService = Mockito.mock(JwtService.class);
        topicController = new TopicController(topicService, jwtService);
    }

    @Test
    void testCreateTopic() {
        String mockUserId = "user123";
        when(jwtService.extractUserData(any(), any())).thenReturn(mockUserId);
        when(topicService.createTopic(any(CreateTopicDto.class), any())).thenReturn(
                new ResponseEntity<>(HttpStatus.CREATED)
        );

        ResponseEntity<?> response = topicController.createTopic(this.testTopic, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetTopics() {
        Pageable pageable = Pageable.ofSize(10);
        Page<TopicResponseDto> mockPage = new PageImpl<>(List.of(testTopicResponse, testTopicResponse));

        when(topicService.getTopics(pageable, null)).thenReturn(mockPage);

        ResponseEntity<Page<TopicResponseDto>> response = topicController.getTopics(pageable, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getContent().size());
    }

    @Test
    void testGetTopicById() {
        Long topicId = 1L;
        GetTopicDto mockTopic = new GetTopicDto(
                "Test topic",
                "Test message",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Test user"
        );
        
        when(topicService.getTopicByDtoId(topicId)).thenReturn(mockTopic);

        ResponseEntity<GetTopicDto> response = topicController.getTopicById(topicId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test topic", Objects.requireNonNull(response.getBody()).getStatement());
    }

    @Test
    void testSearchTopicByStatement() {
        String statement = "Tes";
        List<TopicResponseDto> mockTopics = List.of(this.testTopicResponse, this.testTopicResponse);

        when(topicService.searchTopicByStatement(statement)).thenReturn(mockTopics);

        ResponseEntity<List<TopicResponseDto>> response = topicController.searchTopicByStatement(statement);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testGetTopicsByUser() {
        String mockUserEmail = "user@example.com";
        Pageable pageable = Pageable.ofSize(10);
        Page<TopicResponseDto> mockPage = new PageImpl<>(List.of(this.testTopicResponse));

        when(jwtService.extractUserData(any(), any())).thenReturn(mockUserEmail);
        when(topicService.getTopicsByUser(eq(mockUserEmail), any(Pageable.class))).thenReturn(mockPage);

        ResponseEntity<Page<TopicResponseDto>> response = topicController.getTopicsByUser(pageable, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()), "Response body should not be null");
        assertEquals(1, response.getBody().getContent().size());
    }
}
