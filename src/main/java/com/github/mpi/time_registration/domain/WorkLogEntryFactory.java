package com.github.mpi.time_registration.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkLogEntryFactory {

    private final Pattern WORKLOG_EXPRESSION_PATTERN = Pattern.compile("^([0-9 mhd]+m|h|d)(?:[^#]*)#([a-zA-Z0-9]+)$");
    
    private final EntryIDSequence entryIDSequence;

    public WorkLogEntryFactory(EntryIDSequence entryIDSequence) {
        this.entryIDSequence = entryIDSequence;
    }

    public WorkLogEntry newEntry(String workLogExpression) {
        
        Matcher regexp = WORKLOG_EXPRESSION_PATTERN.matcher(workLogExpression);
        if(regexp.matches()){
            String workload = regexp.group(1);
            String projectName = regexp.group(2);
            return new WorkLogEntry(entryIDSequence.nextID(), Workload.of(workload), new ProjectName(projectName));
        }

        throw new IllegalArgumentException(String.format("Invalid expression: '%s'", workLogExpression));
    }

}
