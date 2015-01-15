package com.phan.webtestapplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.phan.webtestapplication.model.Project;
import com.phan.webtestapplication.model.Script;
import com.phan.webtestapplication.repository.ProjectRepository;
import com.phan.webtestapplication.repository.ScriptRepository;

public class SchedulerService {

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ScriptService scriptService;

    private List<ScheduledFuture<Object>> scheduledFutures = new ArrayList<ScheduledFuture<Object>>();

    @SuppressWarnings("unchecked")
    public void start() {
        for (ScheduledFuture<Object> scheduledFuture : scheduledFutures) {
            scheduledFuture.cancel(false);
        }
        System.err.println("scheduler is destroyed == " + new Date());

        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            final List<Script> scripts = scriptRepository.findByProjectAndShared(project, false);
            if (project.getSchedule() == null || project.getSchedule().isEmpty()) {
                for (final Script script : scripts) {
                    if (script.getSchedule() != null && !script.getSchedule().isEmpty()) {
                        // System.err.println("new scheduler for script# " +
                        // script.getId());
                        scheduledFutures.add(scheduler.schedule(new Runnable() {
                            @Override
                            public void run() {
                                // System.err.println(script.getId() + " == " +
                                // new Date());
                                scriptService.execute(script.getId());
                            }
                        }, new CronTrigger(script.getSchedule())));
                    }
                }
            } else {
                scheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        for (Script script : scripts) {
                            scriptService.execute(script.getId());
                        }
                    }
                }, new CronTrigger(project.getSchedule()));
            }
        }
    }

}
