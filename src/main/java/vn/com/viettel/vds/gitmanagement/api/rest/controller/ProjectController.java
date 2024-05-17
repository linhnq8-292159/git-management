package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.viettel.vds.gitmanagement.application.service.impl.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

import java.io.File;

@RestController
@RequestMapping("${app.base-url}/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService gitLabService) {
        this.projectService = gitLabService;
    }

    @PostMapping
    public Project createProject(@RequestParam String name) {
        return projectService.createProject(name);
    }

    @PostMapping("/{projectName}/files")
    public ResponseEntity<String> uploadFiles(@PathVariable String projectName,
                                              @RequestParam("files") MultipartFile files) {
        try {
            projectService.addFileToProject(projectName, files);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file");
        }


    }
}
