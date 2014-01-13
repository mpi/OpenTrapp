package concordion.reporting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import support.ApiFixture;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.time.Day;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;
import com.github.mpi.time_registration.domain.Workload;

public class ReportingFixture extends ApiFixture {

    @Autowired
    private WorkLogEntryRepository repository;

    public void workLogEntry(String id, String workload, String projectName, String employee, String day) {
        repository.store(new WorkLogEntry(new EntryID(id), Workload.of(workload), new ProjectName(projectName), new EmployeeID(employee), Day.of(day)));
    }

    public void workLogEntry(String id, String workload, String projectName, String employee) {
        repository.store(new WorkLogEntry(new EntryID(id), Workload.of(workload), new ProjectName(projectName), new EmployeeID(employee), null));
    }

    public List<Entry> allWorkLogEntries() throws IllegalAccessException {

        List<Entry> entries = new ArrayList<Entry>();

        for (WorkLogEntry entry : repository.loadAll()) {
            String id = entry.id().toString();
            String workload = "" + entry.workload();
            String project = "" + entry.projectName();
            String employee = "" + entry.employee();
            entries.add(new Entry(id, project, workload, employee));
        }

        return entries;
    }

    public class Entry {

        public String id;
        public String projectName;
        public String workload;
        public String employee;

        public Entry(String id, String project, String workload, String employee) {
            this.id = id;
            this.projectName = project;
            this.workload = workload;
            this.employee = employee;
        }
    }

}
