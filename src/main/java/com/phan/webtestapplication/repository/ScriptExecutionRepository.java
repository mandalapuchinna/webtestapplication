package com.phan.webtestapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phan.webtestapplication.model.Script;
import com.phan.webtestapplication.model.ScriptExecution;

public interface ScriptExecutionRepository extends JpaRepository<ScriptExecution, Long>,
        JpaSpecificationExecutor<ScriptExecution> {

    Page<ScriptExecution> findByScript(Script script, Pageable pageable);

    @Query("SELECT COUNT(e) FROM ScriptExecution e WHERE e.script = :script")
    Long countByScript(@Param("script") Script script);

    ScriptExecution findFirst1ByScriptOrderByIdDesc(Script script);
}
