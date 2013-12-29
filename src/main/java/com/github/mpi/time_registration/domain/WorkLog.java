package com.github.mpi.time_registration.domain;

public interface WorkLog extends Iterable<WorkLogEntry> {

    public WorkLog forProject(ProjectName projectName);
    public WorkLog forEmployee(EmployeeID employeeID);
    
}
