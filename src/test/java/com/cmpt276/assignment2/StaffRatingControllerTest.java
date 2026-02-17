package com.cmpt276.assignment2;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cmpt276.assignment2.controllers.StaffRatingController;
import com.cmpt276.assignment2.models.RoleType;
import com.cmpt276.assignment2.models.StaffRating;
import com.cmpt276.assignment2.models.StaffRatingRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller slice tests using @WebMvcTest with mocked repository.
 */
@WebMvcTest(StaffRatingController.class)
class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffRatingRepository staffRatingRepository;

    private StaffRating sampleRating() {
        StaffRating r = new StaffRating();
        r.setId(1L);
        r.setName("Jane Smith");
        r.setEmail("jane@sfu.ca");
        r.setRoleType(RoleType.TA);
        r.setClarity(8);
        r.setNiceness(9);
        r.setKnowledgeableScore(7);
        r.setComment("Excellent TA");
        return r;
    }

    @Test
    void indexPage_shouldReturn200() throws Exception {
        Mockito.when(staffRatingRepository.findAll()).thenReturn(Arrays.asList(sampleRating()));
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    void viewRating_shouldReturn200() throws Exception {
        Mockito.when(staffRatingRepository.findById(1L)).thenReturn(Optional.of(sampleRating()));
        mockMvc.perform(get("/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("detail"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attributeExists("fancyTitle"));
    }

    @Test
    void addForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("staffRating"));
    }

    @Test
    void editForm_shouldReturn200() throws Exception {
        Mockito.when(staffRatingRepository.findById(1L)).thenReturn(Optional.of(sampleRating()));
        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("staffRating"));
    }

    @Test
    void saveValidRating_shouldRedirect() throws Exception {
        mockMvc.perform(post("/save")
                .param("name", "Test User")
                .param("email", "test@sfu.ca")
                .param("roleType", "PROF")
                .param("clarity", "8")
                .param("niceness", "7")
                .param("knowledgeableScore", "9")
                .param("comment", "Good"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void saveInvalidRating_shouldShowErrors() throws Exception {
        mockMvc.perform(post("/save")
                .param("name", "")
                .param("email", "bad-email")
                .param("roleType", "PROF")
                .param("clarity", "0")
                .param("niceness", "11")
                .param("knowledgeableScore", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().hasErrors());
    }

    @Test
    void deleteRating_shouldRedirect() throws Exception {
        mockMvc.perform(get("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
