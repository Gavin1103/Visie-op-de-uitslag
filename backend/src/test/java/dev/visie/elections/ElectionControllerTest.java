package dev.visie.elections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ElectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetParties() throws Exception {
        mockMvc.perform(get("/election/parties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }

    @Test
    public void testGetPartiesWithStatistics() throws Exception {
        mockMvc.perform(get("/election/parties/votes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));

    }

    @Test
    public void testGetElectedParty() throws Exception {
        mockMvc.perform(get("/election/electedParty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }

    @Test
    public void testGetTotalAmountOfVotes() throws Exception {
        mockMvc.perform(get("/election/totalAmountOfVotes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmountOfVotes", is(notNullValue())))
                .andExpect(jsonPath("$.electoralQuota", is(notNullValue())));
    }

    @Test
    public void testGetPartyById() throws Exception {
        mockMvc.perform(get("/election/party/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partyId", is(1)));
    }

    @Test
    public void testGetCandidatesWithArea() throws Exception {
        mockMvc.perform(get("/election/candidates/{partyId}/{area}/{searchInput}", 1, "CONSTITUENCY", "Amsterdam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }

    @Test
    public void testGetElectionAreas() throws Exception {
        mockMvc.perform(get("/election/areas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.municipalities", is(not(empty()))))
                .andExpect(jsonPath("$.constituencies", is(not(empty()))));
    }

    @Test
    public void testGetTotalVotesByPartyForConstituencies() throws Exception {
        mockMvc.perform(get("/election/totalVotesByParty")
                        .param("constituencies", "Amsterdam,Utrecht"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }

    @Test
    public void testGetWinnersByProvince() throws Exception {
        mockMvc.perform(get("/election/winners-by-province"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
}
