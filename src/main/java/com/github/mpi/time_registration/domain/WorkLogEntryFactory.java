package com.github.mpi.time_registration.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkLogEntryFactory {

    private final Pattern WORKLOG_EXPRESSION_PATTERN = Pattern.compile("^([0-9 mhd]+m|h|d)(?:[^#]*)#([a-zA-Z0-9]+)$");
    
    private final EmployeeContext employeeContext;

    private final EntryIDSequence entryIDSequence;

    public WorkLogEntryFactory(EntryIDSequence entryIDSequence, EmployeeContext employeeContext) {
        this.entryIDSequence = entryIDSequence;
        this.employeeContext = employeeContext;
    }

    public WorkLogEntry newEntry(String workLogExpression) {
        
        Matcher regexp = WORKLOG_EXPRESSION_PATTERN.matcher(workLogExpression);
        if(regexp.matches()){
            
            Workload workload = Workload.of(regexp.group(1));
            ProjectName projectName = new ProjectName(regexp.group(2));
            EmployeeID employeeID = employeeContext.employeeID();

            return new WorkLogEntry(entryIDSequence.nextID(), workload, projectName, employeeID);
        }

        throw new IllegalArgumentException(String.format("Invalid expression: '%s'", workLogExpression));
    }

}
