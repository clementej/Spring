package soccer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import soccer.config.Helper;
import soccer.data.TrainerRepository;
import soccer.entities.Money;
import soccer.entities.Trainer;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/trainers")
public class TrainerController {
    private final String MAX_INTEGER_AS_STRING = "2147483647";//Integer.toString(Integer.MAX_VALUE);
    private TrainerRepository trainerRepository;


    @Autowired
    public TrainerController(TrainerRepository trainerRepository) {
        this.trainerRepository=trainerRepository;
    }

    @Autowired
    Helper helper;

    @RequestMapping(method= RequestMethod.GET)
    List<Trainer> trainers(@RequestParam(value="max",
            defaultValue= MAX_INTEGER_AS_STRING) int max,
                         @RequestParam(value="count", defaultValue="20") int count){
        return trainerRepository.findTrainers(max, count);
    }

    @RequestMapping(value="/show", method=RequestMethod.GET)
    public String showTrainer(
            @RequestParam("trainer_id") String trainerId,
            Model model) {
        model.addAttribute(trainerRepository.findOne(trainerId));
        return "trainer";
    }

    @RequestMapping(value="/{trainerId}", method=RequestMethod.GET)
    public String trainer(
            @PathVariable("trainerId") String trainerId,
            Model model) {
        model.addAttribute(trainerRepository.findOne(trainerId));
        return "trainer";
    }


    @RequestMapping(value="/registertrainer", method=POST)
    public String processRegistration(
            @RequestParam(value="salary", defaultValue="0") BigDecimal salary,
            @Valid Trainer trainer, Errors errors) {
        Money money=new Money(salary);
        trainer.setAnnualSalary(money);
        if (errors.hasErrors()) {
            return "/registertrainer";
        }

        if (!helper.validateTrainer(trainer))
            return "/registertrainer";

        trainerRepository.save(trainer);
        return "redirect:/trainers/" + trainer.getId();
    }


    @RequestMapping(value="/registertrainer", method=GET)
    public String showRegistrationForm() {
        return "registertrainer";
    }

}

