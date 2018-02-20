# shiftschedule
Based on a given set of employees and shifts, compute the best assigned.

Algorithm: Branch and Bound

## definition
Let ``N`` be the natural numbers.

Let ``E`` be a finite set of employees.

Let ``S`` be a finite set of shifts.

### assignment ``a``
Let ``a`` be a vector of employee-shift assignments.

    a : (E,null)^|S| 

We write ``a_s = e`` if Shift ``s`` has been assigned to employee ``e`` by assignment ``a``.

We write ``a_s = null`` if Shift ``s`` has not been assigned yet.

If assignment ``a`` is a complete solution, it holds: every shift ``s`` in ``S`` must have been assigned to an employee ``e`` in ``E``. 

### preference ``p``
Let ``p_min, p_max in N`` with ``p_min<=p_max``.
 
    p : ExS->[p_min,p_max] 

``p(e,s)`` is the preference of employee ``e`` for taking shift ``s``.

Constraint for preference ``p``: 

    avg_{s in C_e}(p(e,s)) equal for every e in E 

(for definition of ``C_e subset S`` compare assignment constraints). 

Here we choose 
    avg = (p_min+p_max)/2

### quality Q
Let ``Q`` be the quality of an assignment ``a``.

    Q : (E,null)^|S| -> N
    Q(a) = sum_{s in S} q(a_s,s)
    q(a_s,s) = p(a_s,s), if a_s != null
    q(a_s,s) = upper bound, else

Simple upper bound:
 
    q(a_s=null,s) = p_max

improved upper bound:

    q(a_s=null,s) = max_{e in E} p(e,s)

further improved upper bound:

    q(a_s=null,s) = max_{e in E} p(e,s), with e available based on constraints on a.

### Assignment Constraints:
#### Constraint: shift candidates ``C_e``
let ``C_e subset S`` be the set of shift candidates of employee ``e``.

``p(e,s)`` may not be defined if Shift ``s`` not in ``C_e``.

#### Constraint: claimed number of shift assignments ``n_e``
Let ``n_e in N_(>=0)`` be the claimed number of shift assignments to employee ``e``.

It must hold:
    
    sum_{e in E} n_e = |S|

#### Constraint: no overlapping shifts
Let Datetime be a combination of date ``dd.mm.yyyy`` and time ``hh:mm:ss:millis``

Let ``T=[t_start,t_stop], t_i in Datetime`` be a timeslice.

Let ``T``, ``V`` be timeslices.

    intersect(T,V)= true iff t_start in V or t_stop in V.

Every shift ``s`` in ``S`` has a timeslice ``T_s`` assigned.

For every pair of shifts ``s,r in S, s != r`` holds:

if both shifts have the same employee assigned, then their timeslices must not intersect.

    if a_s = a_r then intersect(T_s,T_r) = false
			
### Algorithm: Branch & Bound.
Branch along assignment ``a``. Initial ``a = null^S``

Maximize ``q(a)``.

Prioritized depth first search. Node priority based on ``q(a)``.


## Open Tasks
* add tests for classes BranchAndBound + Node (unit + complete test)
* enable fallback shift, if no other shift is available (general support) -> force workdays for employees, use fallback shift. Thus a result contains also a timetable for every employee.
* enable shifts for which the day may vary (eg 10 project days in May). Needs new weighting function
* compute multiple possible (nearly) optimal assignments, so a human may choose in between
* implement GUI


Similar problem: assign student timetable, teacher, classrooms