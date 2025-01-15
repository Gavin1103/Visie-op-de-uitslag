package dev.visie.elections.controller;

import dev.visie.elections.dto.answer.CreateAnswerDto;
import dev.visie.elections.dto.answer.GetAnswerDto;
import dev.visie.elections.model.Answer;
import dev.visie.elections.service.AnswerService;
import dev.visie.elections.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final JwtService jwtService;

    @Autowired
    public AnswerController(AnswerService answerService, JwtService jwtService) {
        this.answerService = answerService;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added answer"),
    })
    public ResponseEntity<Answer> addAnswer(@Valid @RequestBody CreateAnswerDto createAnswerDto, HttpServletRequest request) {
        String userEmail = jwtService.extractUserData(request, "sub");
        Answer answer = answerService.addAnswer(createAnswerDto, userEmail);
        return new ResponseEntity<>(answer, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an answer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted answer"),
            @ApiResponse(responseCode = "404", description = "Answer not found"),
    })
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get answer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Answer not found"),
    })
    public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
        Answer answer = answerService.getAnswerById(id);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicId}")
    @Operation(summary = "Get all answers for a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Answers not found"),
    })
    public ResponseEntity<List<GetAnswerDto>> getAnswersByTopicId(@PathVariable Long topicId) {
        List<GetAnswerDto> answers = answerService.getAnswersByTopicId(topicId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}