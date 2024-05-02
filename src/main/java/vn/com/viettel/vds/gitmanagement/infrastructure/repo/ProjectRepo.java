package vn.com.viettel.vds.gitmanagement.infrastructure.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.FluentQuery;
import vn.com.viettel.vds.gitmanagement.infrastructure.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ProjectRepo extends JpaRepository<Project, Long> {

}
