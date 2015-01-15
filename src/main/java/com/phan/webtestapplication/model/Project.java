package com.phan.webtestapplication.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.phan.webtestapplication.util.validation.ValidSchedule;
import com.phan.webtestapplication.util.validation.ValidServer;

@Entity
public class Project extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Project name is required")
    @NotNull(message = "Project name is required")
    private String name;

    private String description;

    @NotBlank(message = "Project server is required")
    @NotNull(message = "Project server is required")
    @ValidServer(message = "Project server is invalid")
    private String server;

    private String dbClasspath;

    private String dbDriver;

    private String dbUrl;

    private String dbUserid;

    private String dbPassword;

    @ValidSchedule(message = "Project schedule is invalid")
    private String schedule;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Script> scripts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDbClasspath() {
        return dbClasspath;
    }

    public void setDbClasspath(String dbClasspath) {
        this.dbClasspath = dbClasspath;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserid() {
        return dbUserid;
    }

    public void setDbUserid(String dbUserid) {
        this.dbUserid = dbUserid;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public void setScripts(List<Script> scripts) {
        this.scripts = scripts;
    }
}
