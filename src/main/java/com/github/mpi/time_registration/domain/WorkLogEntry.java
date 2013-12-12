package com.github.mpi.time_registration.domain;

public class WorkLogEntry {

    private final EntryID id;
    private Workload workload;
    private ProjectName projectName;

    public WorkLogEntry(EntryID entryID, Workload workload, ProjectName projectName) {
        this.id = entryID;
        this.workload = workload;
        this.projectName = projectName;
    }

    public EntryID id() {
        return id;
    }

    public Workload workload() {
        return workload;
    }

    public ProjectName projectName() {
        return projectName;
    }

    public void updateWorkload(Workload newWorkload) {
        this.workload = newWorkload;
    }

    public void changeProjectTo(ProjectName newProject) {
        this.projectName = newProject;
    }
    
    @Override
    public boolean equals(Object x) {

        if (!(x instanceof WorkLogEntry)) {
            return false;
        }

        WorkLogEntry other = (WorkLogEntry) x;

        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static class EntryID {

        private final String id;

        public EntryID(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object x) {

            if (!(x instanceof EntryID)) {
                return false;
            }

            EntryID other = (EntryID) x;
            return id.equals(other.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return String.format("%s", id);
        }
    }

    public boolean hasSameDataAs(WorkLogEntry other) {

        if (!this.workload.equals(other.workload)) {
            return false;
        }

        if (!this.projectName.equals(other.projectName)) {
            return false;
        }
        
        return true;
    }

}
