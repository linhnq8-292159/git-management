package vn.com.viettel.vds.gitmanagement.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.impl.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    public void createProject(@RequestHeader("PRIVATE-TOKEN") String token, @RequestBody ProjectReq projectReq) {
        projectService.createProject(token, projectReq);
    }

}
