package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;

public enum Constraints {
	Candidate(CandidateConstraint.getInstance()),
	ClaimedNumberOfAssignments(ClaimedNumberOfAssignmentsConstraint.getInstance()),
	NoOverlappingShifts(NoOverlappingShiftsConstraint.getInstance());

	private final Constraint constraint;

	Constraints(Constraint constraint) {
		this.constraint = constraint;
	}

	public Constraint get() {
		return constraint;
	}

}
