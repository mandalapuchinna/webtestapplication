package com.phan.webtestapplication.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.phan.webtestapplication.model.Project;

public interface ReportRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query(value = "SELECT DISTINCT s.id, s.name, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id) AS total, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 2) AS success, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 1) AS failure, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 0) AS running "
            + "FROM script s WHERE s.shared = false AND s.project_id = :projectId", nativeQuery = true)
    List<Object[]> findByProject(@Param("projectId") Long projectId);

    @Query(value = "SELECT DISTINCT s.id, s.name, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.executeddate >= :startdate) AS total, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 2 AND e.executeddate >= :startdate) AS success, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 1 AND e.executeddate >= :startdate) AS failure, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 0 AND e.executeddate >= :startdate) AS running "
            + "FROM script s WHERE s.shared = false AND s.project_id = :projectId", nativeQuery = true)
    List<Object[]> findByProjectAndStartdate(@Param("projectId") Long projectId,
            @Param("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startdate);

    @Query(value = "SELECT DISTINCT s.id, s.name, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.executeddate <= :enddate) AS total, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 2 AND e.executeddate <= :enddate) AS success, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 1 AND e.executeddate <= :enddate) AS failure, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 0 AND e.executeddate <= :enddate) AS running "
            + "FROM script s WHERE s.shared = false AND s.project_id = :projectId", nativeQuery = true)
    List<Object[]> findByProjectAndEnddate(@Param("projectId") Long projectId,
            @Param("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date enddate);

    @Query(value = "SELECT DISTINCT s.id, s.name, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.executeddate >= :startdate AND e.executeddate <= :enddate) AS total, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 2 AND e.executeddate >= :startdate AND e.executeddate <= :enddate) AS success, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 1 AND e.executeddate >= :startdate AND e.executeddate <= :enddate) AS failure, "
            + "(SELECT COUNT(*) FROM scriptexecution e WHERE e.script_id = s.id AND e.status = 0 AND e.executeddate >= :startdate AND e.executeddate <= :enddate) AS running "
            + "FROM script s WHERE s.shared = false AND s.project_id = :projectId", nativeQuery = true)
    List<Object[]> findByProjectAndStartdateAndEnddate(@Param("projectId") Long projectId,
            @Param("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startdate,
            @Param("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date enddate);

}
