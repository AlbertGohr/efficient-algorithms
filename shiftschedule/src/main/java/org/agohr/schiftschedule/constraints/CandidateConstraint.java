package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Assignment;
import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

/**
 * An employee must not be assigned to a shift, if the shift is not part of the employees shift candidate list.
 */
public class CandidateConstraint implements Constraint {

	private static final CandidateConstraint instance = new CandidateConstraint();

	static CandidateConstraint getInstance() {
		return instance;
	}

	private CandidateConstraint() {
		// hidden
	}

	@Override
	public boolean violated(Assignment assignment, Shift shift, Employee employee) {
		return !employee.hasCandidate(shift);
	}

}
