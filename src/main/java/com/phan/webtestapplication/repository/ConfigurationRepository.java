package com.phan.webtestapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.phan.webtestapplication.model.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long>,
        JpaSpecificationExecutor<Configuration> {

    @Query("SELECT e from Configuration e WHERE e.id = 1")
    Configuration findConfig();

}
