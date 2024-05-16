package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.viettel.vds.gitmanagement.application.dto.request.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.dto.response.ProjectRes;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApiService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;
import vn.com.viettel.vds.gitmanagement.infrastructure.repo.ProjectRepo;

import java.io.IOException;

@Service
public class ProjectService {

    @Value("${gitlab.private-token}")
    private String PRIVATE_TOKEN ;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private GitLabApiService gitLabApiService;


    public Project createProject(String projectName) {
        try {
            ProjectReq projectReq = new ProjectReq(projectName);
            Call<ProjectRes> call = gitLabApiService.createProject(PRIVATE_TOKEN, projectReq);
            Response<ProjectRes> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                ProjectRes projectResponse = response.body();
                Project project = new Project();
                project.setName(projectResponse.getName());
                project.setHttpUrl(projectResponse.getHttpUrl());
                return projectRepo.save(project);
            } else {
                throw new RuntimeException("Failed to create project on GitLab");
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            throw new RuntimeException("Failed to create project on GitLab", e);
        }
    }

}
