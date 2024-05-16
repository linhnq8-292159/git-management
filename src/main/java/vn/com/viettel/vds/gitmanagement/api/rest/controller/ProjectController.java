package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.com.viettel.vds.gitmanagement.application.service.impl.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

@RestController
@RequestMapping("${app.base-url}")
public class ProjectController {

    private final ProjectService gitLabService;

    @Autowired
    public ProjectController(ProjectService gitLabService) {
        this.gitLabService = gitLabService;
    }

    @PostMapping("/projects")
    public Project createProject(@RequestParam String name) {
        return gitLabService.createProject(name);
    }
}
