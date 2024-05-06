package vn.com.viettel.vds.gitmanagement.application.service;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface IGitService {
    void cloneRepository(String url, String path) ;
    void addFile(String path, String fileName, String content) ;
    void commit(String path, String message);
    void push(String path) throws IOException, GitAPIException;

}
