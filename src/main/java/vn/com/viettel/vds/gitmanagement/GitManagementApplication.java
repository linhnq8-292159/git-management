package vn.com.viettel.vds.gitmanagement;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import retrofit2.Call;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.ProjectService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class GitManagementApplication {



//    private static final String COMMON_REPO = "https://github.com/nql1211/demo.git";
//    private static final String BRANCH = "main";
//
//    public static Repository createNewRepository() throws IOException {
//        // prepare a new folder
//        File localPath = File.createTempFile("TestGitRepository", "", new File("/tmp/"));
//        if(!localPath.delete()) {
//            throw new IOException("Could not delete temporary file " + localPath);
//        }
//
//        // create the directory
//        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
//        repository.create();
//
//        return repository;
//    }
//
//
//    public static void cloneRepository(String repo) throws IOException, GitAPIException {
//        // prepare a new folder for the cloned repository
//        File localPath = File.createTempFile("GitRepository", "", new File("/tmp/"));
//        if (!localPath.delete()) {
//            throw new IOException("Could not delete temporary file " + localPath);
//        }
//
//        // then clone
//        log.info("Cloning from " + repo + " to " + localPath);
//        try (Git result = Git.cloneRepository()
//                .setURI(repo)
//                .setDirectory(localPath)
//                .setBranch(BRANCH)
//                .call()) {
//            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
//            log.info("Having repository: " + result.getRepository().getDirectory());
//            //delete .git folder from the cloned repository
//            File gitFolder = new File(localPath, ".git");
//            if (gitFolder.exists()) {
//                deleteFolder(gitFolder);
//            }
//
//        }
//    }
//
//    private static void deleteFolder(File gitFolder) {
//        File[] files = gitFolder.listFiles();
//        if (files != null) {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    deleteFolder(file);
//                } else {
//                    file.delete();
//                }
//            }
//        }
//        gitFolder.delete();
//    }
//
//

    @Autowired
    private GitLabApi gitLabApiService;

    @Autowired
    private ProjectService projectService;

    @Scheduled(fixedRate = 1000)
    public void manageProject() {
        Project newProject = new Project();
        newProject.setName("test1123");
        Call<Project> call = gitLabApiService.createProject("glpat-d2BBx28oX7tDE3KQyjsx", new ProjectReq(newProject.getName()));
        try {
            Project createdProject = call.execute().body();
            if (createdProject != null) {
                projectService.saveProject(createdProject);

                String sourceRepoUrl = "https://github.com/github/testrepo.git";
                cloneAndPushRepository(newProject.getName(), createdProject.getHttpUrl(), sourceRepoUrl);
                System.out.println("Repository created: " + createdProject.getHttpUrl());
            } else {
                System.out.println("Error: " + call.execute().message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cloneAndPushRepository(String projectName, String projectWebUrl, String sourceRepoUrl) {

        File localPath = new File("/tmp/" );

        try {
            Git.cloneRepository()
                    .setURI(sourceRepoUrl)
                    .setDirectory(localPath)
                    .call();

            Git git = Git.init().setDirectory(localPath).call();
            git.remoteAdd()
                    .setName("origin")
                    .setUri(new URIish(projectWebUrl + ".git"))
                    .call();

            PushCommand pushCommand = git.push();
            pushCommand.setRemote("origin");
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("glpat-d2BBx28oX7tDE3KQyjsx", ""));
            pushCommand.setForce(true);
            pushCommand.call();

            System.out.println("Repository cloned and pushed successfully");

        } catch (GitAPIException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, GitAPIException {
        SpringApplication.run(GitManagementApplication.class, args);

//        String repositoryPath ;
//        Repository repository = createNewRepository();
//        repositoryPath = repository.getDirectory().getAbsolutePath();
//
//        log.info("Repository was created successfully at " + repositoryPath);
//        cloneRepository(COMMON_REPO);



    }

}
