package vn.com.viettel.vds.gitmanagement.application.service;

import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import vn.com.viettel.vds.gitmanagement.application.dto.request.git.ProjectReq;
import vn.com.viettel.vds.gitmanagement.application.dto.response.ProjectRes;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

@Service
public interface GitLabApiService {

    @POST("/projects")
    Call<ProjectRes> createProject(
            @Header("Private-Token") String token,
            @Body ProjectReq projectReq
            );
}

