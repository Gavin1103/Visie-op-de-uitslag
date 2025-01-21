package dev.visie.elections;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.GetTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.AnswerRepository;
import dev.visie.elections.repository.TopicRepository;
import dev.visie.elections.service.TopicRatingService;
import dev.visie.elections.service.TopicService;
import dev.visie.elections.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    @InjectMocks
    private TopicService topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserService userService;

    @Mock
    private TopicRatingService topicRatingService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTopicById() {
        Topic mockTopic = new Topic();
        mockTopic.setId(1L);
        when(topicRepository.findById(1L)).thenReturn(Optional.of(mockTopic));

        Topic result = topicService.getTopicById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(topicRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTopicByDtoId() {
        Topic mockTopic = new Topic();
        mockTopic.setId(1L);

        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockTopic.setUser(mockUser);

        GetTopicDto mockDto = new GetTopicDto(
                "Test statement",
                "Test message",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "testUser"
        );


        when(topicRepository.findById(1L)).thenReturn(Optional.of(mockTopic));
        when(modelMapper.map(mockTopic, GetTopicDto.class)).thenReturn(mockDto);

        GetTopicDto result = topicService.getTopicByDtoId(1L);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(topicRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTopic() {
        CreateTopicDto createTopicDto = new CreateTopicDto();
        createTopicDto.setMessage("Test Message");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        Topic mockTopic = new Topic();
        mockTopic.setMessage("Test Message");

        when(userService.getUserByEmail("test@example.com")).thenReturn(mockUser);
        when(modelMapper.map(createTopicDto, Topic.class)).thenReturn(mockTopic);
        when(topicRepository.save(any(Topic.class))).thenReturn(mockTopic);

        ResponseEntity<Topic> response = topicService.createTopic(createTopicDto, "test@example.com");

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        verify(topicRepository, times(1)).save(any(Topic.class));
    }

    @Test
    void testGetTopics() {
        Topic mockTopic = new Topic();
        mockTopic.setId(1L);
        mockTopic.setMessage("Test message");

        TopicResponseDto mockDto = new TopicResponseDto();
        mockDto.setId(1L);
        mockDto.setMessage("Test message");

        AmountOfRatingsDTO mockRatings = new AmountOfRatingsDTO();
        mockRatings.setLikes(10);
        mockRatings.setDislikes(2);

        Page<Topic> mockPage = new PageImpl<>(List.of(mockTopic));

        when(topicRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(modelMapper.map(mockTopic, TopicResponseDto.class)).thenReturn(mockDto);
        when(topicRatingService.getAmountOfRatings(mockTopic.getId()))
                .thenReturn(ResponseEntity.ok(mockRatings));
        when(answerRepository.countAnswersByTopicId(mockTopic.getId())).thenReturn(5);

        Page<TopicResponseDto> result = topicService.getTopics(Pageable.ofSize(10), null);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test message", result.getContent().get(0).getMessage());
        assertEquals(10, result.getContent().get(0).getAmountOfRatings().getLikes());
        assertEquals(2, result.getContent().get(0).getAmountOfRatings().getDislikes());
        assertEquals(5, result.getContent().get(0).getAmountOfAnswers());

        verify(topicRepository, times(1)).findAll(any(Pageable.class));
        verify(topicRatingService, times(1)).getAmountOfRatings(mockTopic.getId());
        verify(answerRepository, times(1)).countAnswersByTopicId(mockTopic.getId());
    }


    @Test
    void testSearchTopicByStatement() {
        Topic mockTopic = new Topic();
        mockTopic.setId(1L);
        mockTopic.setMessage("Search Test");

        TopicResponseDto mockDto = new TopicResponseDto();
        mockDto.setId(1L);
        mockDto.setMessage("Search Test");

        AmountOfRatingsDTO mockRatings = new AmountOfRatingsDTO();
        mockRatings.setLikes(5);
        mockRatings.setDislikes(2);

        when(topicRepository.findByStatementContainingIgnoreCase("search")).thenReturn(List.of(mockTopic));
        when(topicRatingService.getAmountOfRatings(mockTopic.getId())).thenReturn(ResponseEntity.ok(mockRatings));
        when(modelMapper.map(mockTopic, TopicResponseDto.class)).thenReturn(mockDto);

        List<TopicResponseDto> result = topicService.searchTopicByStatement("search");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Search Test", result.get(0).getMessage());
        assertEquals(5, result.get(0).getAmountOfRatings().getLikes());
        assertEquals(2, result.get(0).getAmountOfRatings().getDislikes());

        verify(topicRepository, times(1)).findByStatementContainingIgnoreCase("search");
        verify(topicRatingService, times(1)).getAmountOfRatings(mockTopic.getId());
    }

    @Test
    void testGetTopicsByUser() {
        User mockUser = new User();
        mockUser.setEmail("user@example.com");

        Topic mockTopic = new Topic();
        mockTopic.setId(1L);
        mockTopic.setMessage("User Topic");

        TopicResponseDto mockDto = new TopicResponseDto();
        mockDto.setId(1L);
        mockDto.setMessage("User Topic");

        AmountOfRatingsDTO mockRatings = new AmountOfRatingsDTO();
        mockRatings.setLikes(10);
        mockRatings.setDislikes(2);

        Page<Topic> mockPage = new PageImpl<>(List.of(mockTopic));

        when(userService.getUserByEmail("user@example.com")).thenReturn(mockUser);
        when(topicRepository.findByUser(mockUser, Pageable.ofSize(10))).thenReturn(mockPage);
        when(modelMapper.map(mockTopic, TopicResponseDto.class)).thenReturn(mockDto);
        when(topicRatingService.getAmountOfRatings(mockTopic.getId())).thenReturn(ResponseEntity.ok(mockRatings));
        when(answerRepository.countAnswersByTopicId(mockTopic.getId())).thenReturn(5);

        Page<TopicResponseDto> result = topicService.getTopicsByUser("user@example.com", Pageable.ofSize(10));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("User Topic", result.getContent().get(0).getMessage());
        assertEquals(10, result.getContent().get(0).getAmountOfRatings().getLikes());
        assertEquals(2, result.getContent().get(0).getAmountOfRatings().getDislikes());
        assertEquals(5, result.getContent().get(0).getAmountOfAnswers());

        verify(userService, times(1)).getUserByEmail("user@example.com");
        verify(topicRepository, times(1)).findByUser(mockUser, Pageable.ofSize(10));
        verify(topicRatingService, times(1)).getAmountOfRatings(mockTopic.getId());
        verify(answerRepository, times(1)).countAnswersByTopicId(mockTopic.getId());
    }

    @Test
    void testExistsByTitle() {
        when(topicRepository.existsByMessage("Test Title")).thenReturn(true);

        Boolean result = topicService.existsByTitle("Test Title");

        assertTrue(result);
        verify(topicRepository, times(1)).existsByMessage("Test Title");
    }

    @Test
    void testExistsById() {
        when(topicRepository.existsById(1L)).thenReturn(true);

        Boolean result = topicService.existsById(1L);

        assertTrue(result);
        verify(topicRepository, times(1)).existsById(1L);
    }
}
