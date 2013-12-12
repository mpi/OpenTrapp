package concordion.registration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;

import support.ApiFixture;

import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

public class RegistrationFixture extends ApiFixture{

    @Autowired
    private WorkLogEntryRepository repository;
    
    public void successfulRequest(){
        body("{\"workLogExpression\":\"2h 30m on #SomeProject\"}");
        request("POST", "/endpoints/v1/work-log/entries");
    }
    
    public List<Entry> allWorkLogEntries() throws IllegalAccessException{
        
        List<Entry> entries = new ArrayList<RegistrationFixture.Entry>();
        
        for (WorkLogEntry entry : repository.loadAll()) {
            String workload = FieldUtils.readDeclaredField(entry, "workload", true).toString();
            String project = FieldUtils.readDeclaredField(entry, "projectName", true).toString();
            entries.add(new Entry(project, workload));
        }
        
        return entries;
    }

    public class Entry {
        
        public String project;
        public String workload;
        
        public Entry(String project, String workload) {
            this.project = project;
            this.workload = workload;
        }
    }

}


