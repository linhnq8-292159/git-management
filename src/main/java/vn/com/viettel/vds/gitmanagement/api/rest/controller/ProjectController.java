package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.impl.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

import java.io.IOException;

@RestController
public class ProjectController {


    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    public Project createProject(@RequestParam String projectName) throws IOException {
        return projectService.createProject(projectName);
    }

}
