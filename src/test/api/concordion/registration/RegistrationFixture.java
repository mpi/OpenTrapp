package concordion.registration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import support.ApiFixture;

import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

public class RegistrationFixture extends ApiFixture {

    @Autowired
    private WorkLogEntryRepository repository;

    public void successfulRequest() {
        body("{\"workload\":\"2h 30m\", \"projectName\":\"SomeProject\", \"day\":\"2014/01/01\"}");
        request("POST", "/endpoints/v1/employee/Homer/work-log/entries");
    }

    public List<Entry> allWorkLogEntries() throws IllegalAccessException {

        List<Entry> entries = new ArrayList<RegistrationFixture.Entry>();

        for (WorkLogEntry entry : repository.loadAll()) {
            String workload = "" + entry.workload();
            String project = "" + entry.projectName();
            String employee = "" + entry.employee();
            String day = "" + entry.day();
            entries.add(new Entry(project, workload, employee, day));
        }

        return entries;
    }

    public class Entry {

        public String project;
        public String workload;
        public String employee;
        public String day;

        public Entry(String project, String workload, String employee, String day) {
            this.project = project;
            this.workload = workload;
            this.employee = employee;
            this.day = day;
        }
    }
}