package com.linhnq.gitmanagement;

import org.gitlab4j.api.models.Project;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class GitlabService{

    public static void main(String[] args) {

        String baseUrl = "https://gitlab.com/api/v4/";
        String token = "glpat-P78sXgd7MYV7dsdW3yM7";
        String projectName = "test3";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitlabApi service = retrofit.create(GitlabApi.class);
        Call<Project> call = service.createProject(token, new Project().withName(projectName));

        try {
            Response<Project> response = call.execute();
            if (response.isSuccessful()) {
                Project project = response.body();
                assert project != null;
                System.out.println("Project ID: " + project.getHttpUrlToRepo());
            } else {
                System.out.println("Error: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
