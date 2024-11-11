package dev.visie.elections.controller;

import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {

        this.topicService = topicService;
    }

    @PostMapping("/create-topic")
    @Operation(summary = "create topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created topic"),
    })
    public ResponseEntity<String> createTopic(@Valid @RequestBody CreateTopicDto createTopicDto) {

        topicService.createTopic(createTopicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created topic");
    }

    @GetMapping("/")
    @Operation(summary = "Get topics with pagination")
    public ResponseEntity<Page<TopicResponseDto>> getTopics(@PageableDefault(size = 10) Pageable pageable) {

        Page<TopicResponseDto> topics = topicService.getTopics(pageable);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }
}
