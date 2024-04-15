package com.linhnq.gitmanagement;

import org.gitlab4j.api.models.Project;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GitlabApi {

    @Headers({"Content-Type: application/json"})
    @POST("projects")
    Call<Project> createProject(
            @Header("PRIVATE-TOKEN") String token,
            @Body Project project
    );
}

