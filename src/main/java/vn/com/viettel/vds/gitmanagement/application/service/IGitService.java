package vn.com.viettel.vds.gitmanagement.application.service;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface IGitService {
    void cloneRepository(String repoUrl, String cloneDirectoryPath) throws GitAPIException;
    void pushToRepository(String localRepoPath, String remoteRepoUrl) throws IOException, GitAPIException;
    void commitAndPushChanges(String localRepoPath, String filePatten) throws IOException, GitAPIException;
}
