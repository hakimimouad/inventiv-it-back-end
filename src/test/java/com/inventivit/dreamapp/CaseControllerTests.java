package com.inventivit.dreamapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.inventivit.dreamapp.entity.Case;
import com.inventivit.dreamapp.repository.CaseRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CaseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseRepository caseRepository;

    @Test
    public void createCaseTest() throws Exception {
        String newCaseJson = "{\"title\": \"Nouveau cas\", \"description\": \"Description du nouveau cas\"}";
        mockMvc.perform(post("/cases/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCaseJson))
                .andExpect(status().isOk());

        // Assert that the case was created in the database
        Case createdCase = caseRepository.findByTitle("Nouveau cas");
        assertNotNull(createdCase);
        assertEquals("Nouveau cas", createdCase.getTitle());
        assertEquals("Description du nouveau cas", createdCase.getDescription());
    }

    @Test
    public void readCaseByIdTest() throws Exception {
        Case existingCase = new Case();
        existingCase.setTitle("Test case");
        existingCase.setDescription("Test case description");
        caseRepository.save(existingCase);

        mockMvc.perform(get("/cases/{id}", existingCase.getCaseId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test case"))
                .andExpect(jsonPath("$.description").value("Test case description"));
    }

    @Test
    public void updateCaseTest() throws Exception {
        Case existingCase = new Case();
        existingCase.setTitle("Test case");
        existingCase.setDescription("Test case description");
        caseRepository.save(existingCase);

        String updatedCaseJson = "{\"title\": \"Updated title\", \"description\": \"Updated description\"}";
        mockMvc.perform(put("/cases/{id}", existingCase.getCaseId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCaseJson))
                .andExpect(status().isOk());

        // Assert that the case was updated in the database
        Case updatedCase = caseRepository.findById(existingCase.getCaseId()).orElse(null);
        assertNotNull(updatedCase);
        assertEquals("Updated title", updatedCase.getTitle());
        assertEquals("Updated description", updatedCase.getDescription());
    }

    @Test
    public void deleteCaseTest() throws Exception {
        Case existingCase = new Case();
        existingCase.setTitle("Test case");
        existingCase.setDescription("Test case description");
        caseRepository.save(existingCase);

        mockMvc.perform(delete("/cases/{id}", existingCase.getCaseId()))
                .andExpect(status().isOk());

        // Assert that the case was deleted from the database
        assertFalse(caseRepository.existsById(existingCase.getCaseId()));
    }
}

