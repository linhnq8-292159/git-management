package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.springframework.beans.factory.annotation.Value;
import retrofit2.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApi;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;
import vn.com.viettel.vds.gitmanagement.infrastructure.repo.ProjectRepo;

import java.util.List;

@Service
public class ProjectService {

    @Value("${gitlab.private-token}")
    private String PRIVATE_TOKEN ;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private GitService gitService;

    public Project createProject(Project project) {
        Response<Project> response = gitLabApi.createProject(PRIVATE_TOKEN, project).execute();

    }

    public Project saveProject(Project project) {
        return projectRepo.save(project);
    }

    public String getProjectName(Long id) {
        return projectRepo.findById(id).get().getName();
    }

    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }

}
