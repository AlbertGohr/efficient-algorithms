package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Value
public class Employees {

	@Getter(AccessLevel.NONE)
	private final Set<Employee> employees;

	public Employees(Set<Employee> employees) {
		this.employees = Collections.unmodifiableSet(new HashSet<>(employees));
	}

	public Stream<Employee> stream() {
		return employees.stream();
	}

	public boolean contains(Employee employee) {
		return employees.contains(employee);
	}

}
