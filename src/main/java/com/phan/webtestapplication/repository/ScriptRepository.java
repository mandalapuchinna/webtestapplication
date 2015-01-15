package com.phan.webtestapplication.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phan.webtestapplication.model.Project;
import com.phan.webtestapplication.model.Script;

public interface ScriptRepository extends JpaRepository<Script, Long>, JpaSpecificationExecutor<Script> {

    List<Script> findByProjectAndShared(Project project, boolean shared);

    Page<Script> findByProjectAndShared(Project project, boolean shared, Pageable pageable);

    @Query("SELECT COUNT(e) FROM Script e WHERE e.project = :project AND e.shared = :shared")
    Long countByProject(@Param("project") Project project, @Param("shared") boolean shared);

}
