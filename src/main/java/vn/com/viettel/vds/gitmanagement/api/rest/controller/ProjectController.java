package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.vds.gitmanagement.application.service.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

}
