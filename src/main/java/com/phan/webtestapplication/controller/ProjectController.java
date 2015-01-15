package com.phan.webtestapplication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.phan.webtestapplication.Constants;
import com.phan.webtestapplication.model.Project;
import com.phan.webtestapplication.model.Script;
import com.phan.webtestapplication.repository.ProjectRepository;
import com.phan.webtestapplication.repository.ScriptRepository;
import com.phan.webtestapplication.service.SchedulerService;
import com.phan.webtestapplication.service.ScriptService;
import com.phan.webtestapplication.util.GeneralUtil;
import com.phan.webtestapplication.util.pagination.PageRequest;
import com.phan.webtestapplication.util.pagination.Pagination;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public String openProject(Model model, @PathVariable("projectId") Long projectId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        Project project = projectRepository.findOne(projectId);

        model.addAttribute("title", GeneralUtil.getPageTitle(project.getName()));
        model.addAttribute("project", project);
        model.addAttribute(
                "normalScripts",
                scriptRepository.findByProjectAndShared(project, false,
                        new PageRequest(page, Constants.PAGINATION_MAX_PER_PAGE, new Sort(Sort.Direction.DESC, "id")))
                        .getContent());
        model.addAttribute(
                "sharedScripts",
                scriptRepository.findByProjectAndShared(project, true,
                        new PageRequest(page, Constants.PAGINATION_MAX_PER_PAGE, new Sort(Sort.Direction.DESC, "id")))
                        .getContent());
        model.addAttribute("pagination1", new Pagination(page, scriptRepository.countByProject(project, false)));
        model.addAttribute("pagination2", new Pagination(page, scriptRepository.countByProject(project, false)));

        return "project";
    }

    @RequestMapping(value = "/project/{projectId}/edit", method = RequestMethod.POST)
    public String updateProject(@ModelAttribute("project") @Valid Project temp, BindingResult errors, Model model,
            @PathVariable("projectId") Long projectId) {

        Project project = projectRepository.findOne(projectId);
        project.setName(temp.getName());
        project.setDescription(temp.getDescription());
        project.setServer(temp.getServer());
        project.setDbClasspath(temp.getDbClasspath());
        project.setDbDriver(temp.getDbDriver());
        project.setDbUrl(temp.getDbUrl());
        project.setDbUserid(temp.getDbUserid());
        project.setDbPassword(temp.getDbPassword());
        project.setSchedule(temp.getSchedule());

        model.addAttribute("title", GeneralUtil.getPageTitle(project.getName()));
        model.addAttribute("project", project);

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            return "project";
        } else {
            projectRepository.save(project);
            schedulerService.start();

            return "redirect:/project/{projectId}";
        }
    }

    @RequestMapping(value = "/project/{projectId}/delete", method = RequestMethod.POST)
    public String deleteProject(Model model, @PathVariable("projectId") Long projectId) {

        projectRepository.delete(projectRepository.findOne(projectId));

        return "redirect:/";
    }

    @RequestMapping(value = "/project/{projectId}/newscript", method = RequestMethod.POST)
    public String newScript(@ModelAttribute("script") @Valid Script script, BindingResult errors, Model model,
            @PathVariable("projectId") Long projectId) {

        Project project = projectRepository.findOne(projectId);
        model.addAttribute("title", GeneralUtil.getPageTitle(project.getName()));
        model.addAttribute("project", project);

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            return "project";
        } else {
            project.getScripts().add(script);
            projectRepository.save(project);

            return "redirect:/project/{projectId}";
        }
    }

    @RequestMapping(value = "/project/{projectId}/execute", method = RequestMethod.POST)
    public String executeScripts(Model model, @PathVariable("projectId") Long projectId,
            @RequestParam(value = "scriptId[]") Long[] scriptIds) {

        for (Long scriptId : scriptIds) {
            scriptService.execute(scriptId);
        }

        return "redirect:/project/{projectId}";
    }
}
