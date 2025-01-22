## Backend tests
### Example of a test for the Authentication endpoint
```
    @Test
    public void testAuthenticate() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setEmail(userDto.getEmail());
        jwtRequest.setPassword(userDto.getPassword());

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk());
    }

```
- [Entire file](https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-r-se-cs/semester-1/siiquujuucii98/-/blob/main/backend/src/test/java/dev/visie/elections/AuthenticationControllerTest.java?ref_type=heads)

### Backend tests being ran by GitLab CI
(screenshot of tests being ran by GitLab CI)

- [Entire file](https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-r-se-cs/semester-1/siiquujuucii98/-/blob/main/.gitlab-ci.tests.yml?ref_type=heads)

## Frontend tests
### Example of the test for the login page 
- [Entire file](https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-r-se-cs/semester-1/siiquujuucii98/-/blob/main/frontend/cypress/e2e/login.cy.ts?ref_type=heads)


