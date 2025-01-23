## Wireframe and prototypes

![Wireframe and Prototypes](./documentation/wireframe-prototype-overview.png)
-  [Click here to see the designs in full](https://miro.com/app/board/uXjVLdw4xoY=/)

## ERD
### ERD Design

![ERD](./documentation/erd-design.png)
- [Click here to see in full](https://lucid.app/lucidchart/7d9b915d-70ea-4a6f-9597-307f37fe7174/edit?invitationId=inv_88ebd740-1a4a-4159-81ee-54d334bbf2ab&page=0_0#)

### Generated ERD by DataGrip

![ERD](./documentation/erd.png)

### Class diagram

![ERD](./documentation/class-diagram.png)
- [Click to see in full](https://lucid.app/lucidchart/764afbe1-f78a-47ee-b405-f28b40ccee59/edit?invitationId=inv_65a81139-14b6-42e8-9eb3-b5fd41304a68&page=0_0#)

### Domain Diagram
```mermaid
classDiagram
    class User {
        +Long id
        +String username
        +String email
        +String password
        +boolean enabled
        +Set~Role~ roles
        +List~Topic~ topics
    }

    class Topic {
        +Long id
        +String statement
        +String message
        +User user
        +List~Answer~ answers
    }

    class Answer {
        +Long id
        +String message
        +User user
        +Topic topic
        +List~Comment~ comments
    }

    class Comment {
        +Long id
        +String message
        +User user
        +Answer answer
    }

    User "1" --> "0..*" Topic
    User "1" --> "0..*" Answer
    Topic "1" --> "0..*" Answer
    Answer "1" --> "0..*" Comment
```


### Implementation diagram
```mermaid
classDiagram
    class UserService {
        +User getUserById(Long id)
        +List~User~ getAllUsers()
        +User getUserByEmail(String email)
        +User createUser(User user)
        +User updateUser(Long id, User user)
        +void deleteUser(Long id)
    }

    class TopicService {
        +Topic getTopicById(Long id)
        +Page~Topic~ getTopics(Pageable pageable, String customSort)
        +List~Topic~ searchTopicByStatement(String statement)
        +Page~Topic~ getTopicsByUser(String userEmail, Pageable pageable)
        +ResponseEntity~Topic~ createTopic(CreateTopicDto createTopicDto, String userId)
    }

    class AnswerService {
        +Answer getAnswerById(Long id)
        +List~Answer~ getAnswersByTopicId(Long topicId)
        +Answer addAnswer(CreateAnswerDto createAnswerDto, String userEmail)
        +void deleteAnswer(Long id)
    }

    class CommentService {
        +Comment getCommentById(Long id)
        +List~Comment~ getCommentsByAnswerId(Long answerId)
        +Comment addComment(CreateCommentDto createCommentDto, String userEmail)
        +void deleteComment(Long id)
    }

    class UserController {
        +UserService userService
        +JwtService jwtService
        +ResponseEntity~List~User~~ getAllUsers()
        +ResponseEntity~User~ getUserByEmail(String email)
        +ResponseEntity~User~ getUserById(Long id)
        +ResponseEntity~User~ deleteUserById(Long userId)
        +ResponseEntity~?~ editUser(UpdateUserDTO userDto, Long id)
        +ResponseEntity~?~ editSelf(UpdateUserDTO userDto, HttpServletRequest request)
        +ResponseEntity~UserProfileResponse~ getUserByToken(String token)
    }

    class TopicController {
        +TopicService topicService
        +JwtService jwtService
        +ResponseEntity~Topic~ createTopic(CreateTopicDto createTopicDto, HttpServletRequest request)
        +ResponseEntity~Page~TopicResponseDto~~ getTopics(Pageable pageable, String customSort)
        +ResponseEntity~GetTopicDto~ getTopicById(Long id)
        +ResponseEntity~List~TopicResponseDto~~ searchTopicByStatement(String statement)
        +ResponseEntity~Page~TopicResponseDto~~ getTopicsByUser(Pageable pageable, HttpServletRequest request)
    }

    class AnswerController {
        +AnswerService answerService
        +JwtService jwtService
        +ResponseEntity~Answer~ addAnswer(CreateAnswerDto createAnswerDto, HttpServletRequest request)
        +ResponseEntity~Void~ deleteAnswer(Long id)
        +ResponseEntity~Answer~ getAnswerById(Long id)
        +ResponseEntity~List~GetAnswerDto~~ getAnswersByTopicId(Long topicId)
    }

    class CommentController {
        +CommentService commentService
        +JwtService jwtService
        +ResponseEntity~Comment~ addComment(CreateCommentDto createCommentDto, HttpServletRequest request)
        +ResponseEntity~Void~ deleteComment(Long id)
        +ResponseEntity~Comment~ getCommentById(Long id)
        +ResponseEntity~List~GetCommentDto~~ getCommentsByAnswerId(Long answerId)
    }

    UserController --> UserService
    TopicController --> TopicService
    AnswerController --> AnswerService
    CommentController --> CommentService
```