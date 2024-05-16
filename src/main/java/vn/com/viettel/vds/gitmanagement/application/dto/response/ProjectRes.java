package vn.com.viettel.vds.gitmanagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ProjectRes {
    private Long id;
    private String name;
    private String httpUrl;

}
