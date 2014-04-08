package com.github.mpi.time_registration.infrastructure.persistence.mongo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.ProjectNames;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.time.Day;

public class MongoProjectNames implements ProjectNames {

    private final MongoTemplate mongo;
    
    private Day after;

    public MongoProjectNames(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public Iterator<ProjectName> iterator() {

        Criteria criteria = new Criteria();
        if(after != null) {
            criteria = Criteria.where("day.date").gte(after.toString());
        }
    
        List<WorkLogEntry> all = mongo.find(Query.query(criteria), WorkLogEntry.class);

        Set<ProjectName> names = new HashSet<ProjectName>();
        for (WorkLogEntry workLogEntry: all){
            names.add(workLogEntry.projectName());
        }
        return names.iterator();
    }

    @Override
    public ProjectNames after(Day after) {
        this.after = after;
        return this;
    }
}
