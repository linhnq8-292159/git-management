package vn.com.viettel.vds.gitmanagement.retrofit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GitLabProjectResponse {
    private Long id;
    private String name;
    private String url;
}
