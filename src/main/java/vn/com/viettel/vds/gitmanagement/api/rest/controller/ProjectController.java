package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.impl.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;



@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService gitLabService;

    @Autowired
    public ProjectController(ProjectService gitLabService) {
        this.gitLabService = gitLabService;
    }

    @PostMapping
    public Project createProject(@RequestHeader("Private-Token") String token, @RequestBody ProjectReq projectRequest) {
        return gitLabService.createAndSaveProject(token, projectRequest);
    }
}