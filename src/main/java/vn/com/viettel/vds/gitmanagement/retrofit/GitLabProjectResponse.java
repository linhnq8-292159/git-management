package vn.com.viettel.vds.gitmanagement.retrofit;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GitLabProjectResponse {
    private Long id;

    private String name;

    @SerializedName("http_url_to_repo")
    private String url;
}
