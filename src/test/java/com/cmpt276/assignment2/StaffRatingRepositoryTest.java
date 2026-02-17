package com.cmpt276.assignment2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.cmpt276.assignment2.models.RoleType;
import com.cmpt276.assignment2.models.StaffRating;
import com.cmpt276.assignment2.models.StaffRatingRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Repository / persistence tests using H2 in-memory database.
 */
@DataJpaTest
@ActiveProfiles("test")
class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository repository;

    private StaffRating createRating(String name, String email) {
        StaffRating r = new StaffRating();
        r.setName(name);
        r.setEmail(email);
        r.setRoleType(RoleType.PROF);
        r.setClarity(8);
        r.setNiceness(7);
        r.setKnowledgeableScore(9);
        r.setComment("Test comment");
        return r;
    }

    @Test
    void saveAndRetrieve_shouldWork() {
        StaffRating saved = repository.save(createRating("Alice", "alice@sfu.ca"));
        assertNotNull(saved.getId());

        Optional<StaffRating> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getName());
        assertEquals("alice@sfu.ca", found.get().getEmail());
        assertEquals(RoleType.PROF, found.get().getRoleType());
        assertEquals(8, found.get().getClarity());
        assertEquals(7, found.get().getNiceness());
        assertEquals(9, found.get().getKnowledgeableScore());
    }

    @Test
    void delete_shouldRemoveEntry() {
        StaffRating saved = repository.save(createRating("Bob", "bob@sfu.ca"));
        Long id = saved.getId();
        repository.deleteById(id);
        Optional<StaffRating> found = repository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void timestamps_shouldBeSetOnSave() {
        StaffRating saved = repository.save(createRating("Carol", "carol@sfu.ca"));
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void findAll_returnsAllEntries() {
        repository.save(createRating("Dan", "dan@sfu.ca"));
        repository.save(createRating("Eve", "eve@sfu.ca"));
        assertEquals(2, repository.findAll().size());
    }
}
