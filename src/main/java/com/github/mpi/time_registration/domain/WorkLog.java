package com.github.mpi.time_registration.domain;

import com.github.mpi.time_registration.domain.time.Period;

public interface WorkLog extends Iterable<WorkLogEntry> {

    public WorkLog forProject(ProjectName projectName);
    public WorkLog forEmployee(EmployeeID employeeID);

    public WorkLog in(Period months);
}
