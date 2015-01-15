package com.baesystems.webtestapplication.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.BasicProject;
import com.atlassian.jira.rest.client.domain.input.IssueInput;

public class JiraService {

    @Autowired
    private JiraRestClient jira;

    @Autowired
    private NullProgressMonitor pm;

    public Iterable<BasicProject> getAllProjects() {
        return jira.getProjectClient().getAllProjects(pm);
    }

    public void createIssue() {
        // TODO: just a blank IssueInput
        IssueInput issue = new IssueInput(null);
        jira.getIssueClient().createIssue(issue, pm);
    }

}
