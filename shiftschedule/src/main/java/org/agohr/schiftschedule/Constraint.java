package org.agohr.schiftschedule;

import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

public interface Constraint {

	/**
	 * @param assignment current assignment
	 * @param shift next selected shift to be assigned
	 * @param employee next selected employee to be assigned
	 * @param nextAssignment results after shift and employee have been assigned to assignment
	 * @return true iff the constraint has been violated
	 */
	boolean violated(Assignment assignment, Shift shift, Employee employee, Assignment nextAssignment);

}
