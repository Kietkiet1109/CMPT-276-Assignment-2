package com.cmpt276.assignment2;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cmpt276.assignment2.models.RoleType;
import com.cmpt276.assignment2.models.StaffRating;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for StaffRating validation constraints using Jakarta Validator.
 */
class StaffRatingValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private StaffRating validRating() {
        StaffRating r = new StaffRating();
        r.setName("John Doe");
        r.setEmail("john@example.com");
        r.setRoleType(RoleType.PROF);
        r.setClarity(8);
        r.setNiceness(7);
        r.setKnowledgeableScore(9);
        r.setComment("Great professor");
        return r;
    }

    @Test
    void validRating_noViolations() {
        StaffRating r = validRating();
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertTrue(violations.isEmpty(), "A valid rating should have no violations");
    }

    @Test
    void blankName_shouldFail() {
        StaffRating r = validRating();
        r.setName("");
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void nullName_shouldFail() {
        StaffRating r = validRating();
        r.setName(null);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidEmail_shouldFail() {
        StaffRating r = validRating();
        r.setEmail("not-an-email");
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void blankEmail_shouldFail() {
        StaffRating r = validRating();
        r.setEmail("");
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
    }

    @Test
    void clarityBelowMin_shouldFail() {
        StaffRating r = validRating();
        r.setClarity(0);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("clarity")));
    }

    @Test
    void clarityAboveMax_shouldFail() {
        StaffRating r = validRating();
        r.setClarity(11);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nicenessOutOfRange_shouldFail() {
        StaffRating r = validRating();
        r.setNiceness(0);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
    }

    @Test
    void knowledgeScoreOutOfRange_shouldFail() {
        StaffRating r = validRating();
        r.setKnowledgeableScore(11);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nullScores_shouldFail() {
        StaffRating r = validRating();
        r.setClarity(null);
        r.setNiceness(null);
        r.setKnowledgeableScore(null);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertEquals(3, violations.size());
    }

    @Test
    void commentTooLong_shouldFail() {
        StaffRating r = validRating();
        r.setComment("a".repeat(501));
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("comment")));
    }

    @Test
    void commentExactly500_shouldPass() {
        StaffRating r = validRating();
        r.setComment("a".repeat(500));
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullComment_shouldPass() {
        StaffRating r = validRating();
        r.setComment(null);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(r);
        assertTrue(violations.isEmpty());
    }
}
