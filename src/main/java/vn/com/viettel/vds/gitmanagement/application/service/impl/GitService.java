package vn.com.viettel.vds.gitmanagement.application.service.impl;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import vn.com.viettel.vds.gitmanagement.application.service.IGitService;

import java.io.File;
import java.io.IOException;

@Service
public class GitService implements IGitService {


    private final CredentialsProvider credentialsProvider;

    public GitService() {
        credentialsProvider = new UsernamePasswordCredentialsProvider("linhnq8", "123123a@");
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
    public void addFile(String path, String fileName, String content) throws IOException, GitAPIException {
        Git.open(new File(path))
                .add()
                .addFilepattern(".")
                .call();
    }


    @Override
    public void push(String path) throws IOException, GitAPIException {
        Git.open(new File(path))
                .push()
                .setCredentialsProvider(credentialsProvider)
                .call();
    }
}
