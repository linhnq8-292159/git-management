package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.com.viettel.vds.gitmanagement.application.service.impl.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

@RestController
@RequestMapping("${app.base-url}/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public Project createProject(@RequestParam String projectName) {
        return projectService.createProject(projectName);
    }

}
