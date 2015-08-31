package soccer;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import soccer.config.Factory;
import soccer.data.PlayerRepository;
import soccer.entities.Player;
import soccer.web.PlayerController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PlayerControllerTests {

    Factory factory =new Factory();

    @DirtiesContext
    @Test
    public void testPlayer() throws Exception {
        Player expectedPlayer = factory.generateRandomPlayer();
        PlayerRepository mockRepository = mock(PlayerRepository.class);
        when(mockRepository.findOne(expectedPlayer.getId())).thenReturn(expectedPlayer);
        PlayerController controller = new PlayerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/players/"+expectedPlayer.getId()))
                .andExpect(view().name("player"))
                .andExpect(model().attributeExists("player"))
                .andExpect(model().attribute("player", expectedPlayer));
    }

    @DirtiesContext
    @Test
    public void testShowPlayer() throws Exception {
        Player expectedPlayer = factory.generateRandomPlayer();
        PlayerRepository mockRepository = mock(PlayerRepository.class);
        when(mockRepository.findOne(expectedPlayer.getId())).thenReturn(expectedPlayer);
        PlayerController controller = new PlayerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/players/show?player_id="+expectedPlayer.getId()))
                .andExpect(view().name("player"))
                .andExpect(model().attributeExists("player"))
                .andExpect(model().attribute("player", expectedPlayer));
    }

    @DirtiesContext
    @Test
    public void shouldShowPagedPlayers() throws Exception {
        List<Player> expectedPlayers = createPlayerList(50);
        PlayerRepository mockRepository = mock(PlayerRepository.class);
        when(mockRepository.findPlayers(238900, 50))
                .thenReturn(expectedPlayers);
        PlayerController controller =
                new PlayerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("/WEB-INF/views/players.jsp"))
                .build();
        mockMvc.perform(get("/players?max=238900&count=50"))
                .andExpect(view().name("players"))
                .andExpect(model().attributeExists("playerList"))
                .andExpect(model().attribute("playerList",
                        hasItems(expectedPlayers.toArray())));
    }
    @DirtiesContext
    @Test
    public void shouldShowRecentPlayers() throws Exception {
        List<Player> expectedPlayers = createPlayerList(20);
        PlayerRepository mockRepository =
                mock(PlayerRepository.class);
        when(mockRepository.findPlayers(Integer.MAX_VALUE, 20))
                .thenReturn(expectedPlayers);
        PlayerController controller =
                new PlayerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("/WEB-INF/views/players.jsp"))
                .build();
        mockMvc.perform(get("/players"))
                .andExpect(view().name("players"))
                .andExpect(model().attributeExists("playerList"))
                .andExpect(model().attribute("playerList",
                        hasItems(expectedPlayers.toArray())));
    }

    private List<Player> createPlayerList(int count) throws IOException {
        List<Player> players = new ArrayList<Player>();
        for (int i=0; i < count; i++) {
            players.add(factory.generateRandomPlayer());
        }
        return players;
    }
}
