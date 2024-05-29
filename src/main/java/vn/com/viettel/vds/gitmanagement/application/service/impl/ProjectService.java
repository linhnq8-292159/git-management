package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.Objects;

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
                gitService.pushToRepository(localRepo.getAbsolutePath(), projectResponse.getHttpUrl());

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

    public void addFileToProject(String projectName, MultipartFile file) throws GitAPIException, IOException {
        File projectDir = new File(SOURCE_FOLDER + projectName);
        if (!projectDir.exists()) {
            gitService.cloneRepository(projectRepo.findByName(projectName).getHttpUrl(), projectDir.getAbsolutePath());
        }
        gitService.pullLatestChanges(projectDir.getAbsolutePath());
        File destFile = new File(projectDir, Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(destFile);

        gitService.commitAndPushChanges(projectDir.getAbsolutePath(), file.getOriginalFilename());

    }

}
