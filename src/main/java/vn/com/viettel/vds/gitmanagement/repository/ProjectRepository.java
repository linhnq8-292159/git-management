package vn.com.viettel.vds.gitmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.viettel.vds.gitmanagement.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
