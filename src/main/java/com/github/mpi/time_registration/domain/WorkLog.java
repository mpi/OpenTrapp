package com.github.mpi.time_registration.domain;

import com.github.mpi.time_registration.domain.time.Day;

public interface WorkLog extends Iterable<WorkLogEntry> {

    public WorkLog forProject(ProjectName projectName);
    public WorkLog forEmployee(EmployeeID employeeID);
    public WorkLog before(Day day);
    public WorkLog after(Day day);
    
}
