package com.phan.webtestapplication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.phan.webtestapplication.Constants;
import com.phan.webtestapplication.model.Configuration;
import com.phan.webtestapplication.model.Project;
import com.phan.webtestapplication.repository.ConfigurationRepository;
import com.phan.webtestapplication.repository.ProjectRepository;
import com.phan.webtestapplication.util.GeneralUtil;
import com.phan.webtestapplication.util.pagination.PageRequest;
import com.phan.webtestapplication.util.pagination.Pagination;

@Controller
public class HomeController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        model.addAttribute("title", GeneralUtil.getPageTitle("Homepage"));

        Configuration configuration = configurationRepository.findConfig();
        if (configuration == null) {
            return "redirect:/configuration";
        } else {
            model.addAttribute(
                    "projects",
                    projectRepository.findAll(
                            new PageRequest(page, Constants.PAGINATION_MAX_PER_PAGE,
                                    new Sort(Sort.Direction.DESC, "id"))).getContent());
            model.addAttribute("pagination", new Pagination(page, projectRepository.count()));
            return "home";
        }
    }

    @RequestMapping(value = "/newproject", method = RequestMethod.POST)
    public String newProject(@ModelAttribute("project") @Valid Project project, BindingResult errors, Model model) {

        model.addAttribute("title", GeneralUtil.getPageTitle("Homepage"));

        model.addAttribute("projects", projectRepository.findAll(new Sort(Sort.Direction.DESC, "id")));

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            return "home";
        } else {
            projectRepository.save(project);
            return "redirect:/";
        }
    }
}
