package vn.com.viettel.vds.gitmanagement.application.service.impl;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApi;
import vn.com.viettel.vds.gitmanagement.application.service.IGitService;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

import java.io.File;
import java.io.IOException;

@Service
public class GitService implements IGitService {


    private final CredentialsProvider credentialsProvider;

    public GitService() {
        credentialsProvider = new UsernamePasswordCredentialsProvider("linhnq8", "123123a@");
    }

    public void createProject() throws IOException{

        String baseUrl = "http://gitlab.orc.com/api/v4/";
//        String token = "glpat-d2BBx28oX7tDE3KQyjsx";
        String projectName = "test1123";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitLabApi service = retrofit.create(GitLabApi.class);

        ProjectReq request = new ProjectReq(projectName);

        Call<Project> call = service.createProject(token, request);
        
        call.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    Project repository = response.body();
                    if (repository != null)
                        System.out.println("Repository created: " + repository.getHttpUrl());
                } else {
                        System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
    public void commit(String path, String message) {

    }

    @Override
    public void push(String path) throws IOException, GitAPIException {
        Git.open(new File(path))
                .push()
                .setCredentialsProvider(credentialsProvider)
                .call();
    }
}
