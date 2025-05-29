//package dev.visie.elections;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.visie.elections.dto.JwtRequest;
//import dev.visie.elections.dto.user.CreateUserDTO;
//import dev.visie.elections.model.enums.RoleEnum;
//import dev.visie.elections.service.AuthenticationService;
//import dev.visie.elections.service.UserService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthenticationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private AuthenticationService authenticationService;
//
//    @MockBean
//    private UserService userService;
//
//    private CreateUserDTO userDto;
//
//    @BeforeEach
//    public void setUp() {
//        userDto = new CreateUserDTO(
//                "Test",
//                "test@test.com",
//                "0612345678", RoleEnum.USER);
//
//        try {
//            authenticationService.register(userDto, RoleEnum.USER, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testRegister() throws Exception {
//        CreateUserDTO newUserDto = new CreateUserDTO(
//                "newuser",
//                "newUser@test.com",
//                "0612345678",
//                RoleEnum.USER
//        );
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newUserDto)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testAuthenticate() throws Exception {
//        JwtRequest jwtRequest = new JwtRequest();
//        jwtRequest.setEmail(userDto.getEmail());
//        jwtRequest.setPassword(userDto.getPassword());
//
//        mockMvc.perform(post("/auth/authenticate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(jwtRequest)))
//                .andExpect(status().isOk());
//    }
//
//    @AfterEach
//    public void tearDown() {
//        userService.deleteUserByEmail(userDto.getEmail());
//    }
//}