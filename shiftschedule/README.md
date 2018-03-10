# shiftschedule
Based on a given set of employees and shifts, compute the best assigned.

Algorithm: Branch and Bound

## 1. Next Tasks
* enable fallback shift, if no other shift is available (general support) -> force workdays for employees, use fallback shift. Thus a result contains also a timetable for every employee.
* enable shifts for which the day may vary (eg 10 project days in May). Needs new weighting function
    * multiple shifts with varying days may belong together.
* add a location to every shift. Employees can only be assigned if their location constraints are fulfilled.
* add a shift type property to every shift (e.g. developer-shift, support-shift).  Employees can only be assigned if they match the shift-type.
* shift start and shift end may vary inside a given timeslice
* special shift: weekend standby
* compute multiple possible (nearly) optimal assignments, so a human may choose in between
* implement GUI

## 2. definition

Let ``E`` be a finite set of employees.

Let ``S`` be a finite set of shifts.

### 2.1 assignment ``a``
Let ``a`` be a vector of employee-shift assignments.

![math](https://latex.codecogs.com/svg.latex?\mathbf{a}:(E\cup\{\bot\})^{|S|})


We write ![math](https://latex.codecogs.com/svg.latex?\mathbf{a}_s=e) if Shift ``s`` has been assigned to employee ``e`` by assignment ``a``.

We write ![math](https://latex.codecogs.com/svg.latex?\mathbf{a}_s=\bot) if Shift ``s`` has not been assigned yet.

If assignment ``a`` is a complete solution, it holds: every shift ``s`` in ``S`` must have been assigned to an employee ``e`` in ``E``. 
![math](https://latex.codecogs.com/svg.latex?\forall{s}\in{S}:\mathbf{a}_s\neq\bot)

### 2.2 preference ``p``
Let ![math](https://latex.codecogs.com/svg.latex?p_{min},p_{max}\in\mathbb{N},p_{min}\leq{p_{max}})
 
![math](https://latex.codecogs.com/svg.latex?\varphi:E\times{S}\to[p_{min},p_{max}])

![math](https://latex.codecogs.com/svg.latex?\varphi(e,s)) is the preference of employee ``e`` for taking shift ``s``.

Constraint for preference ``p``: 

![math](https://latex.codecogs.com/svg.latex?\exists{p_{avg}}:p_{avg}=\overline{\{\varphi(e,s)|{s\in{C_{e}}}\}}\forall{e}\in{E})

In words: the average of preferences must be equal for every employee.

(for definition of candidates ![math](https://latex.codecogs.com/svg.latex?C_e\subseteq{S}) compare assignment constraints). 

Here we choose ![math](https://latex.codecogs.com/svg.latex?p_{avg}=\frac{(p_{min}+p_{max})}{2},p_{min}\equiv{p}_{max}mod2)

### 2.3 quality Q
Let ![math](https://latex.codecogs.com/svg.latex?\varrho(\mathbf{a})) be the quality of an assignment ``a``.

![math](https://latex.codecogs.com/svg.latex?\varrho:(E\cup\{\bot\})^{|S|}\rightarrow\mathbb{N})

![math](https://latex.codecogs.com/svg.latex?\mathbf{\varrho}(\mathbf{a})=\sum_{s\in{S}}\varrho(\mathbf{a}_s,s))

![math](https://latex.codecogs.com/svg.latex?\varrho(e,s)=\varphi(e,s))

![math](https://latex.codecogs.com/svg.latex?\varrho(\bot,s)=upperBound)

Simple upper bound:

![math](https://latex.codecogs.com/svg.latex?\varrho(\bot,s)=p_{max})

improved upper bound:

![math](https://latex.codecogs.com/svg.latex?\varrho(\bot,s)={max}_{e\in{E}}\varphi(e,s))

further improved upper bound:

![math](https://latex.codecogs.com/svg.latex?\varrho(\bot,s)={max}_{e\in{E}}\varphi(e,s)), with ``e`` available based on constraints on ``a``.

### 2.4 Assignment Constraints:
#### Constraint: shift candidates ``C_e``
let  ![math](https://latex.codecogs.com/svg.latex?C_e\subseteq{S}) be the set of shift candidates of employee ``e``.

![math](https://latex.codecogs.com/svg.latex?\varphi(e,s)) may not be defined if Shift ``s`` not in ![math](https://latex.codecogs.com/svg.latex?C_e).

#### Constraint: claimed number of shift assignments ``n_e``
Let ![math](https://latex.codecogs.com/svg.latex?n_e\in\mathbb{N}_{\geq0}) be the claimed number of shift assignments to employee ``e``.

It must hold:

![math](https://latex.codecogs.com/svg.latex?\sum_{e\in{E}}n_e=|S|)

#### Constraint: no overlapping shifts
Let Datetime be a combination of date ``dd.mm.yyyy`` and time ``hh:mm:ss:millis``

Let ``T=[t_start,t_stop], t_i in Datetime`` be a timeslice.

Let ``T``, ``V`` be timeslices.

    intersect(T,V)= true iff t_start in V or t_stop in V.

Every shift ``s`` in ``S`` has a timeslice ``T_s`` assigned.

For every pair of shifts ``s,r in S, s != r`` holds:

if both shifts have the same employee assigned, then their timeslices must not intersect.

    if a_s = a_r then intersect(T_s,T_r) = false
			
### 2.5 Algorithm: Branch & Bound.
Branch along assignment ``a``. Initial ![math](https://latex.codecogs.com/svg.latex?a=\bot^S)

Maximize ![math](https://latex.codecogs.com/svg.latex?\varrho(\mathbf{a})).

Prioritized depth first search. Node priority based on ![math](https://latex.codecogs.com/svg.latex?\varrho(\mathbf{a})).

## 3. Backlog

Similar problem: assign student timetable, teacher, classrooms