# shiftschedule
Based on a given set of employees and shifts, compute the best assigned.

Algorithm: Branch and Bound

## Open Tasks
* add tests
  * QualityEvaluator
  * Assignment getNextUnassignedShift
  * all upperBounds
  * ClaimedNumberOfAssignmentsConstraint
  * OrderedConstraints
  * ExpireCheck
  * BranchAndBound + Node
* enable fallback shift, if no other shift is available (general support) -> force workdays for employees, use fallback shift
* enable shifts for which the day may vary (eg 10 project days in May). Needs new weighting function
* compute multiple possible (nearly) optimal assignments
* implement GUI


Similar problem: assign student timetable, teacher, classrooms