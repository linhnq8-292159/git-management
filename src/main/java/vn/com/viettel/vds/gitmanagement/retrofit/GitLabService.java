package vn.com.viettel.vds.gitmanagement.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GitLabService {

    @POST("projects")
    Call<ProjectResponse> createProject(@Header("PRIVATE-TOKEN") String token, @Body ProjectRequest projectRequest);

}