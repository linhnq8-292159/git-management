package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApiService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;
import vn.com.viettel.vds.gitmanagement.infrastructure.repo.ProjectRepo;

import java.io.IOException;

@Service
public class ProjectService {

    private static final String BASE_URL = "https://gitlab.orc.com/api/v4/";
    private final ProjectRepo projectRepository;
    private final GitLabApiService gitLabService;

    @Autowired
    public ProjectService(ProjectRepo projectRepository) {
        this.projectRepository = projectRepository;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitLabService = retrofit.create(GitLabApiService.class);
    }

    public Project createAndSaveProject(String token, ProjectReq projectRequest) {
        Call<Project> call = gitLabService.createProject(token, projectRequest);
        try {
            retrofit2.Response<Project> response = call.execute();
            if (response.isSuccessful()) {
                Project project = response.body();
                return projectRepository.save(project);
            } else {
                // Handle error
                throw new RuntimeException("Failed to create project: " + response.errorBody().string());
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to create project", e);
        }
    }
}
