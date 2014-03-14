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

}
