package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.eclipse.jgit.api.errors.GitAPIException;
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

import java.io.File;
import java.io.IOException;

@Service
public class ProjectService {

    @Value("${gitlab.private-token}")
    private String PRIVATE_TOKEN ;

    @Value("${source.folder}")
    private String SOURCE_FOLDER ;

    @Value("${base.repo.url}")
    private String BASE_REPO_URL ;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private GitLabApiService gitLabApiService;

    @Autowired
    private GitService gitService;

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



                //Create local repository and push to remote repository
                File localRepo = new File(SOURCE_FOLDER + projectResponse.getName());
                if (!localRepo.exists()) {
                    localRepo.mkdir();
                }

                gitService.cloneRepository(BASE_REPO_URL, localRepo.getAbsolutePath());
                gitService.pushToRepository(localRepo.getAbsolutePath(), projectResponse.getHttpUrl(), "root", "123123a@");

                return projectRepo.save(project);
            } else {
                throw new RuntimeException("Failed to create project on GitLab");
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            throw new RuntimeException("Failed to create project on GitLab", e);
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

}
