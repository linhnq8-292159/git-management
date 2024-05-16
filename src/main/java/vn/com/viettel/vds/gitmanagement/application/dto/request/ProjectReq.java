package vn.com.viettel.vds.gitmanagement.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectReq {
    private String name;
    
}
