package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.schedule.ScheduleController;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(classes = CritterApplication.class)
@AutoConfigureMockMvc
public class CritterControllerIntegrationTest {

    @Autowired
    ScheduleController scheduleController;

    @Autowired
    PetController petController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenPostRequestToSchedulesAndInvalidSchedule_thenCorrectResponse() throws Exception{
        String schedule = "{\"employeeIds\": null, \"petIds\": null, \"workday\": null, \"activities\": [\"PETTING\", \"FEEDING\"]}";
        mockMvc.perform(MockMvcRequestBuilders.post("/schedule")
                .content(schedule)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Errors[*]", hasSize(3)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToPetsAndInvalidPet_thenCorrectResponse() throws Exception{
        String pet = "{\"type\": \"CAT\", \"name\": \"Kilo\", \"ownerId\": null, \"birthDate\": \"2019-12-16T04:43:57.995Z\", \"notes\": \"HI KILO\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/pet")
                .content(pet).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Errors[0]", Is.is("OwnerId must be greater than or equal to 1")))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
