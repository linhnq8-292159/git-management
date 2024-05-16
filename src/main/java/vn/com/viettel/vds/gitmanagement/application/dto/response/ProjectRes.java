package vn.com.viettel.vds.gitmanagement.application.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ProjectRes {
    private String name;
    @SerializedName("http_url_to_repo")
    private String httpUrl;

}
