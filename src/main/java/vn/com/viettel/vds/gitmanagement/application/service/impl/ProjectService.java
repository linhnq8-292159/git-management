package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApiService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;
import vn.com.viettel.vds.gitmanagement.infrastructure.repo.ProjectRepo;

import java.io.IOException;

@Service
public class ProjectService {

    @Value("${gitlab.private-token}")
    private String PRIVATE_TOKEN ;

    @Value("${gitlab.url}")
    private String BASE_REPO_URL;

    @Value("${source.folder}")
    private String SOURCE_FOLDER;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private GitLabApiService gitLabApiService;

    @Autowired
    private GitService gitService;

    public Project createProject(String token, ProjectReq projectReq) {
//        Call<Project> call = gitLabApiService.createProject(token, projectReq);
//        call.enqueue(new Callback<Project>() {
//            @Override
//            public void onResponse(Call<Project> call, Response<Project> response) {
//                if (response.isSuccessful()) {
//                    Project project = response.body();
//                    try {
//                        gitService.cloneRepository(project.getHttpUrl(), SOURCE_FOLDER + project.getName());
//                    } catch (GitAPIException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Project> call, Throwable t) {
//                System.out.println("Failed to create project");
//            }
//        });
//        return null;
        Call<Project> call = gitLabApiService.createProject(token, projectReq);
        try {
            retrofit2.Response<Project> response = call.execute();
            if (response.isSuccessful()) {
                Project project = response.body();
                return projectRepo.save(project);
            } else {
                // Handle error
                throw new RuntimeException("Failed to create project: " + response.errorBody().string());
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to create project", e);
        }
    }

    public Project saveProject(Project project) {
        return projectRepo.save(project);
    }
//
//    public String getProjectName(Long id) {
//        return projectRepo.findById(id).get().getName();
//    }

    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }

}
