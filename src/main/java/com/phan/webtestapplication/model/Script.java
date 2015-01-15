package com.phan.webtestapplication.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.phan.webtestapplication.util.validation.ValidSchedule;

@Entity
public class Script extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Script name is required")
    @NotNull(message = "Script name is required")
    private String name;

    @Column(columnDefinition = "CLOB")
    private String content;

    @ValidSchedule(message = "Script schedule is invalid")
    private String schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "script_id")
    private List<ScriptExecution> scriptExecutions;

    @NotNull(message = "Script type is required")
    private boolean shared;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ScriptExecution> getScriptExecutions() {
        return scriptExecutions;
    }

    public void setScriptExecutions(List<ScriptExecution> scriptExecutions) {
        this.scriptExecutions = scriptExecutions;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

}
