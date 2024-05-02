package vn.com.viettel.vds.gitmanagement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

public interface GitLabApi {

    @Headers({"Content-Type: application/json"})
    @POST("projects")
    Call<Project> createProject(
            @Header("PRIVATE-TOKEN") String token,
            @Body ProjectReq projectReq
    );
}

