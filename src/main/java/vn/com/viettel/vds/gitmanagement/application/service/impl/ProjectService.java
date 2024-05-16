package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.dto.response.ProjectRes;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApiService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;
import vn.com.viettel.vds.gitmanagement.infrastructure.repo.ProjectRepo;

import java.io.IOException;
import java.util.regex.Pattern;

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
    private final Pattern projectNamePattern = Pattern.compile("^[\\w\\s.+-]*$");


    public Project createProject(String projectName) throws IOException {
        if (!isValidProjectName(projectName)) {
            throw new IllegalArgumentException("Project name can contain only letters, digits, emoji, '_', '.', '+', dashes, or spaces. It must start with a letter, digit, emoji, or '_'.");
        }
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
                logError(response);
                if (response.code() == 401) {
                    throw new RuntimeException("Unauthorized: Invalid GitLab token");
                } else {
                    throw new RuntimeException("Failed to create project on GitLab: " + response.message());
                }
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            throw new RuntimeException("Failed to create project on GitLab", e);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new RuntimeException("Failed to create project on GitLab", e);
        }
    }

    private boolean isValidProjectName(String projectName) {
        return projectName != null && projectNamePattern.matcher(projectName).matches() && !projectName.startsWith(".");
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
