package dev.visie.elections.controller;

import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    @PostMapping("/create-topic")
    @Operation(summary = "create topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created topic"),
    })
    public ResponseEntity<Topic> createTopic(@Valid @RequestBody CreateTopicDto createTopicDto) {
        Topic createdTopic = topicService.createTopic(createTopicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

}
