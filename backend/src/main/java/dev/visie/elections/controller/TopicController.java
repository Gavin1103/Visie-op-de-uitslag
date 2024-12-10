package dev.visie.elections.controller;

import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.service.JwtService;
import dev.visie.elections.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;
    private final JwtService jwtService;

    @Autowired
    public TopicController(TopicService topicService, JwtService jwtService) {
        this.topicService = topicService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create-topic")
    @Operation(summary = "create topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created topic"),
    })
    public ResponseEntity<Topic> createTopic(@Valid @RequestBody CreateTopicDto createTopicDto, HttpServletRequest request) {
        String id = this.jwtService.extractUserData(request, "sub");
        return topicService.createTopic(createTopicDto, id);
    }

    @GetMapping("/")
    @Operation(summary = "Get topics with pagination")
    public ResponseEntity<Page<TopicResponseDto>> getTopics(@PageableDefault(size = 10) Pageable pageable) {

        Page<TopicResponseDto> topics = topicService.getTopics(pageable);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get topic by id", description = "Get a topic by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Topic not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @GetMapping("/search/{statement}")
    @Operation(summary = "Search topic by statement")
    public ResponseEntity<List<TopicResponseDto>> searchTopicByStatement(@PathVariable String statement) {

        List<TopicResponseDto> topics = topicService.searchTopicByStatement(statement);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }
}
