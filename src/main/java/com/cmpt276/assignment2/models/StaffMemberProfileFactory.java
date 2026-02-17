package com.cmpt276.assignment2.models;

/**
 * Factory that maps a RoleType enum to its corresponding StaffMemberProfile
 * subclass.
 * Uses polymorphism to return the correct display title for each role.
 */
public class StaffMemberProfileFactory {

    public static StaffMemberProfile fromRole(RoleType roleType) {
        switch (roleType) {
            case PROF:
                return new ProfessorProfile();
            case TA:
                return new TAProfile();
            case INSTRUCTOR:
                return new InstructorProfile();
            case STAFF:
                return new StaffProfile();
            default:
                return new StaffProfile();
        }
    }
}
