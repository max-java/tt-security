package com.tutrit.ttsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("unsecure")
public class UnsecureControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
