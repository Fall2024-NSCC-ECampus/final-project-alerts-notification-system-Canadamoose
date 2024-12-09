package com.example.notifsystemapi.controller;

import com.example.notifsystemapi.exceptions.NoDataFoundException;
import com.example.notifsystemapi.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FireStationControllerTest {

    private final FireStationService fireStationService = Mockito.mock(FireStationService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new FireStationController(fireStationService)).build();

    @Test
    void testGetEmailsByCity_emptyResponse() throws Exception {
        Mockito.when(fireStationService.getEmailsByCity("UnknownCity"))
                .thenThrow(new NoDataFoundException("No emails found"));

        mockMvc.perform(get("/communityEmail?city=UnknownCity")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void testGetEmailsByCity_validCity() throws Exception {
        Mockito.when(fireStationService.getEmailsByCity("Springfield"))
                .thenReturn(Collections.singletonList("john.doe@example.com"));

        mockMvc.perform(get("/communityEmail?city=Springfield")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"john.doe@example.com\"]"));
    }
}
