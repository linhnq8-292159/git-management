package vn.com.viettel.vds.gitmanagement.application.service;

import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import vn.com.viettel.vds.gitmanagement.application.dto.request.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.dto.response.ProjectRes;

@Service
public interface GitLabApiService {

    @POST("projects")
    Call<ProjectRes> createProject(
            @Header("PRIVATE-TOKEN") String token,
            @Body ProjectReq projectReq
            );
}

