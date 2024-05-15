package vn.com.viettel.vds.gitmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.vds.gitmanagement.entity.Project;
import vn.com.viettel.vds.gitmanagement.service.GitLabService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final GitLabService gitLabService;

    @Autowired
    public ProjectController(GitLabService gitLabService) {
        this.gitLabService = gitLabService;
    }

    @PostMapping
    public Project createProject(@RequestParam String name) {
        return gitLabService.createGitLabProject(name);
    }
}