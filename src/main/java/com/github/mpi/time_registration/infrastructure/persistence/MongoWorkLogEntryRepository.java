package com.github.mpi.time_registration.infrastructure.persistence;

import java.util.Iterator;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLog;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;
import com.github.mpi.time_registration.domain.time.Day;

public class MongoWorkLogEntryRepository implements WorkLogEntryRepository {

    private final MongoTemplate mongo;

    public MongoWorkLogEntryRepository(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void store(WorkLogEntry entry) throws WorkLogEntryAlreadyExists {

        assertEntryDoesNotExist(entry.id());
        mongo.insert(entry);
    }

    private void assertEntryDoesNotExist(EntryID id) {
        if(find(id) != null){
            throw new WorkLogEntryAlreadyExists(id);
        }
    }

    @Override
    public WorkLogEntry load(EntryID entryID) throws WorkLogEntryDoesNotExists {
        WorkLogEntry found = find(entryID);
        if (found == null)
            throw new WorkLogEntryDoesNotExists(entryID);
        return found;
    }

    private WorkLogEntry find(EntryID entryID) {
        return mongo.findOne(withID(entryID), WorkLogEntry.class);
    }

    private Query withID(EntryID entryID) {
        return Query.query(Criteria.where("id").is(entryID));
    }

    @Override
    public WorkLog loadAll() {
        return new MongoWorkLog();
    }

    private final class MongoWorkLog implements WorkLog {
        
        private EmployeeID employeeID;
        private ProjectName projectName;
        private Day start;
        private Day end;
        
        @Override
        public Iterator<WorkLogEntry> iterator() {
            return mongo.find(buildQuery(), WorkLogEntry.class).iterator();
        }
        
        private Query buildQuery() {
            
            Criteria criteria = new Criteria();
            if(employeeID != null)
                criteria.and("employeeID").is(employeeID);
            if(projectName != null)
                criteria.and("projectName").is(projectName);
            if(start != null)
                criteria.and("day").gte(start);
            if(end != null)
                criteria.and("day").lte(end);
            return Query.query(criteria);
        }
        
        @Override
        public WorkLog forProject(ProjectName projectName) {
            this.projectName = projectName;
            return this;
        }
        
        @Override
        public WorkLog forEmployee(EmployeeID employeeID) {
            this.employeeID = employeeID;
            return this;
        }
        
        @Override
        public WorkLog before(Day day) {
            this.end = day;
            return this;
        }
        
        @Override
        public WorkLog after(Day day) {
            this.start = day;
            return this;
        }
    }
    
}
