package vn.com.viettel.vds.gitmanagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import vn.com.viettel.vds.gitmanagement.entity.Project;
import vn.com.viettel.vds.gitmanagement.repository.ProjectRepository;
import vn.com.viettel.vds.gitmanagement.retrofit.GitLabService;
import vn.com.viettel.vds.gitmanagement.retrofit.ProjectRequest;
import vn.com.viettel.vds.gitmanagement.retrofit.ProjectResponse;

import java.io.IOException;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private final String gitLabToken = "glpat-SXUAxyMzvzkGWnyDs_Nq";

    @Autowired
    private GitLabService gitLabApi;

    public Project createGitLabProject(String projectName) {
        try {
            ProjectRequest request = new ProjectRequest(projectName);
            Call<ProjectResponse> call = gitLabApi.createProject(gitLabToken, request);
            Response<ProjectResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                ProjectResponse projectResponse = response.body();
                Project project = new Project();
                project.setName(projectResponse.getName());
                project.setUrl(projectResponse.getUrl());
                return projectRepository.save(project);
            } else {
                logError(response);
                if (response.code() == 401) {
                    throw new RuntimeException("Unauthorized: Invalid GitLab token");
                } else {
                    throw new RuntimeException("Failed to create project on GitLab: " + response.message());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create project on GitLab", e);
        }
    }

    private void logError(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                System.err.println("Error body: " + response.errorBody().string());
            }
        } catch (IOException e) {
            System.err.println("Error reading errorBody: " + e.getMessage());
        }
        System.err.println("Response code: " + response.code());
        System.err.println("Response message: " + response.message());
    }
}