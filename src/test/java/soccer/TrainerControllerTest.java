package soccer;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import soccer.config.Factory;
import soccer.data.TrainerRepository;
import soccer.entities.Trainer;
import soccer.web.TrainerController;

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
public class TrainerControllerTest {
    Factory factory =new Factory();

    @DirtiesContext
    @Test
    public void testTrainer() throws Exception {
        Trainer expectedTrainer = factory.generateRandomTrainer();
        TrainerRepository mockRepository = mock(TrainerRepository.class);
        when(mockRepository.findOne(expectedTrainer.getId())).thenReturn(expectedTrainer);
        TrainerController controller = new TrainerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/trainers/"+expectedTrainer.getId()))
                .andExpect(view().name("trainer"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().attribute("trainer", expectedTrainer));
    }

    @DirtiesContext
    @Test
    public void testShowTrainer() throws Exception {
        Trainer expectedTrainer = factory.generateRandomTrainer();
        TrainerRepository mockRepository = mock(TrainerRepository.class);
        when(mockRepository.findOne(expectedTrainer.getId())).thenReturn(expectedTrainer);
        TrainerController controller = new TrainerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/trainers/show?trainer_id="+expectedTrainer.getId()))
                .andExpect(view().name("trainer"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().attribute("trainer", expectedTrainer));
    }

    @DirtiesContext
    @Test
    public void shouldShowPagedTrainers() throws Exception {
        List<Trainer> expectedTrainers = createTrainerList(50);
        TrainerRepository mockRepository = mock(TrainerRepository.class);
        when(mockRepository.findTrainers(238900, 50))
                .thenReturn(expectedTrainers);
        TrainerController controller =
                new TrainerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("/WEB-INF/views/trainers.jsp"))
                .build();
        mockMvc.perform(get("/trainers?max=238900&count=50"))
                .andExpect(view().name("trainers"))
                .andExpect(model().attributeExists("trainerList"))
                .andExpect(model().attribute("trainerList",
                        hasItems(expectedTrainers.toArray())));
    }
    @DirtiesContext
    @Test
    public void shouldShowRecentTrainers() throws Exception {
        List<Trainer> expectedTrainers = createTrainerList(20);
        TrainerRepository mockRepository =
                mock(TrainerRepository.class);
        when(mockRepository.findTrainers(Integer.MAX_VALUE, 20))
                .thenReturn(expectedTrainers);
        TrainerController controller =
                new TrainerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("/WEB-INF/views/trainers.jsp"))
                .build();
        mockMvc.perform(get("/trainers"))
                .andExpect(view().name("trainers"))
                .andExpect(model().attributeExists("trainerList"))
                .andExpect(model().attribute("trainerList",
                        hasItems(expectedTrainers.toArray())));
    }

    private List<Trainer> createTrainerList(int count) throws IOException {
        List<Trainer> trainers = new ArrayList<Trainer>();
        for (int i=0; i < count; i++) {
            trainers.add(factory.generateRandomTrainer());
        }
        return trainers;
    }
}
