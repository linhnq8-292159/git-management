package vn.com.viettel.vds.gitmanagement.infrastructure.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;


public interface ProjectRepo extends JpaRepository<Project, Long> {
    Project findByName(String name);
}
