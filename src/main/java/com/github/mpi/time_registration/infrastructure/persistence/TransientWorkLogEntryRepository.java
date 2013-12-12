package com.github.mpi.time_registration.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.mpi.time_registration.domain.WorkLog;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

public class TransientWorkLogEntryRepository implements WorkLogEntryRepository {

    private List<WorkLogEntry> store = new ArrayList<WorkLogEntry>();
    
    @Override
    public WorkLog loadAll() {
        return new WorkLog(){
            @Override
            public Iterator<WorkLogEntry> iterator() {
                return store.iterator();
            }
        };
    }

    @Override
    public void store(WorkLogEntry entry) {
        if(find(entry.id()) != null){
            throw new WorkLogEntryAlreadyExists(entry.id());
        }
        store.add(entry);
    }

    @Override
    public WorkLogEntry load(EntryID entryID) {

        WorkLogEntry entry = find(entryID);
        if(entry != null){
            return entry;
        }
        
        throw new WorkLogEntryDoesNotExists(entryID);
    }

    private WorkLogEntry find(EntryID entryID) {

        for (WorkLogEntry entry : store) {
            if(entryID.equals(entry.id())){
                return entry;
            }
        }
        
        return null;
    }

}
