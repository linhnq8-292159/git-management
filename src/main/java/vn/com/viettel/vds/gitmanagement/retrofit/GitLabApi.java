package vn.com.viettel.vds.gitmanagement.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.com.viettel.vds.gitmanagement.entity.Project;

public interface GitLabApi {

    @POST("projects")
    Call<GitLabProjectResponse> createProject(@Header("PRIVATE-TOKEN") String token, @Body GitLabProjectRequest projectRequest);

}