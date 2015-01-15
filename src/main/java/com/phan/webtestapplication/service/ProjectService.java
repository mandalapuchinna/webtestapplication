package com.phan.webtestapplication.service;

import java.net.URL;

import com.phan.webtestapplication.model.Project;

public class ProjectService {

    public URL getProjectServer(Project project) {
        try {
            return new URL(project.getServer());
        } catch (Exception e) {
            return null;
        }
    }

}
