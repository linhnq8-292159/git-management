package vn.com.viettel.vds.gitmanagement.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "project")
public class Git {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "repo_name")
    private String repoName;

    @Column(name = "url")
    private String url;

    @Column(name = "path")
    private String path;
}
