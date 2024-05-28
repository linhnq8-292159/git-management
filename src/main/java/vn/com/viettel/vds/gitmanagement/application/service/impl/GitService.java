package vn.com.viettel.vds.gitmanagement.application.service.impl;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import vn.com.viettel.vds.gitmanagement.application.service.IGitService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class GitService implements IGitService {


    private final CredentialsProvider credentialsProvider;

    public GitService() {
        credentialsProvider = new UsernamePasswordCredentialsProvider("root", "123123a@");
    }


    @Override
    public void cloneRepository(String url, String path) throws GitAPIException {
        Git.cloneRepository()
                .setURI(url)
                .setDirectory(new File(path))
                .setCredentialsProvider(credentialsProvider)
                .call();
    }

    @Override
    public void pushToRepository(String localRepoPath, String remoteRepoUrl) throws IOException, GitAPIException {
        try (Git git = Git.open(new File(localRepoPath))) {
            git.remoteSetUrl()
                    .setRemoteName("origin")
                    .setRemoteUri(new URIish(remoteRepoUrl))
                    .call();

            git.push()
                    .setCredentialsProvider(credentialsProvider)
                    .call();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commitAndPushChanges(String localRepoPath, String filePatten) throws IOException, GitAPIException {
        try (Git git = Git.open(new File(localRepoPath))) {
            git.add()
                    .addFilepattern(filePatten)
                    .call();

            git.commit()

                    .setMessage("Add file " + filePatten)
                    .call();

            git.push()
                    .setCredentialsProvider(credentialsProvider)
                    .call();
        }
    }

}
