package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.schedule.ScheduleController;
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
public class ScheduleControllerIntegrationTest {

    @Autowired
    ScheduleController scheduleController;

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

}
