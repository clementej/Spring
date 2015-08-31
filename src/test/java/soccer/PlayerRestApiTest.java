package soccer;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import soccer.config.Factory;
import soccer.entities.Player;
import soccer.web.PlayerApiController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PlayerRestApiTest {


    Factory factory =new Factory();

    @DirtiesContext
    @Test
    public void testRestPlayerApi() throws Exception{
        PlayerApiController mockResource = Mockito.mock(PlayerApiController.class);
        Player player= factory.generateRandomPlayer();
        String playerId = player.getId();
        mockResource.savePlayer(player);
        MockMvc mockMvc =
                standaloneSetup(mockResource).build();
        mockMvc.perform(get("/playersapi/")).andExpect(status().isOk());
        mockMvc.perform(get("/playersapi/"+playerId)).andExpect(status().isOk());
    }
}
