package vn.com.viettel.vds.gitmanagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.viettel.vds.gitmanagement.entity.Project;
import vn.com.viettel.vds.gitmanagement.repository.ProjectRepository;
import vn.com.viettel.vds.gitmanagement.retrofit.GitLabApi;
import vn.com.viettel.vds.gitmanagement.retrofit.GitLabProjectRequest;
import vn.com.viettel.vds.gitmanagement.retrofit.GitLabProjectResponse;

import java.io.IOException;


@Service
public class GitLabService {

    private final ProjectRepository projectRepository;
    private final GitLabApi gitLabApi;
    private final String gitLabToken = "glpat-SXUAxyMzvzkGWnyDs_Nq";

    @Autowired
    public GitLabService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gitlab.com/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.gitLabApi = retrofit.create(GitLabApi.class);
    }

    public Project createGitLabProject(String projectName) {
        try {
            GitLabProjectRequest request = new GitLabProjectRequest(projectName);
            Call<GitLabProjectResponse> call = gitLabApi.createProject(gitLabToken, request);
            Response<GitLabProjectResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                GitLabProjectResponse projectResponse = response.body();
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