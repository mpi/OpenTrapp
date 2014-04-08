package com.github.mpi.time_registration.application;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.ProjectNames;

@Controller
public class ProjectNamesEndpoint {

    @Autowired
    private ProjectNames projectNames;
    
    @RequestMapping(
            method = GET, 
          produces = "application/json", 
             value = "/endpoints/v1/projects")
    @ResponseBody
    public List<String> list() {

        List<String> results = new ArrayList<String>();
        for (ProjectName name : projectNames) {
            results.add(name.toString());
        }
        return results;
    }
}
