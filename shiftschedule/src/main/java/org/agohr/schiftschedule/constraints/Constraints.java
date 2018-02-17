package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;

public enum Constraints {
	Candidate(new CandidateConstraint()),
	ClaimedNumberOfAssignments(new ClaimedNumberOfAssignmentsConstraint()),
	NoOverlappingShifts(new NoOverlappingShiftsConstraint());

	private final Constraint constraint;

	Constraints(Constraint constraint) {
		this.constraint = constraint;
	}

	public Constraint get() {
		return constraint;
	}

}
