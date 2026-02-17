package com.cmpt276.assignment2.models;

/**
 * Abstract base class for staff member profiles.
 * Demonstrates polymorphism: each role provides its own displayTitle().
 */
public abstract class StaffMemberProfile {

    /**
     * Returns a human-friendly display title for the staff member's role.
     * Each concrete subclass provides its own implementation.
     */
    public abstract String displayTitle();
}
