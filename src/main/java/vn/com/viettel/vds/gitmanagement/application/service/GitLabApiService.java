package vn.com.viettel.vds.gitmanagement.application.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import vn.com.viettel.vds.gitmanagement.application.dto.request.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.dto.response.ProjectRes;

public interface GitLabApiService {
    @POST("projects")
    Call<ProjectRes> createProject(@Header("PRIVATE-TOKEN") String token, @Body ProjectReq projectRequest);
}

