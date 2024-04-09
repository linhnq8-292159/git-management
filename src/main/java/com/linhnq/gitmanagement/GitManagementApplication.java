package com.linhnq.gitmanagement;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.AccessLog;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@Slf4j
public class GitManagementApplication {

    private static final String COMMON_REPO = "https://github.com/nql1211/demo.git";
    private static final String BRANCH = "master";

    public static Repository createNewRepository() throws IOException {
        // prepare a new folder
        File localPath = File.createTempFile("TestGitRepository", "", new File("/tmp/"));
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

        return repository;
    }


    public static void cloneRepository(String repo) throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("GitRepository", "", new File("/tmp/"));
        if (!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        log.info("Cloning from " + repo + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(repo)
                .setDirectory(localPath)
                .setBranch(BRANCH)
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            log.info("Having repository: " + result.getRepository().getDirectory());
            //delete .git folder from the cloned repository
            File gitFolder = new File(localPath, ".git");
            if (gitFolder.exists()) {
                deleteFolder(gitFolder);
            }

        }
    }

    private static void deleteFolder(File gitFolder) {
        File[] files = gitFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        gitFolder.delete();
    }


    public static void main(String[] args) throws IOException, GitAPIException {
//        SpringApplication.run(GitManagementApplication.class, args);

        String repositoryPath ;
        Repository repository = createNewRepository();
        repositoryPath = repository.getDirectory().getAbsolutePath();

        log.info("Repository was created successfully at " + repositoryPath);
        cloneRepository(COMMON_REPO);



    }

}
