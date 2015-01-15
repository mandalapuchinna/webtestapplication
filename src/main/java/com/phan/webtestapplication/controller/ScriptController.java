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
import com.phan.webtestapplication.model.Script;
import com.phan.webtestapplication.repository.ProjectRepository;
import com.phan.webtestapplication.repository.ScriptExecutionRepository;
import com.phan.webtestapplication.repository.ScriptRepository;
import com.phan.webtestapplication.service.SchedulerService;
import com.phan.webtestapplication.service.ScriptService;
import com.phan.webtestapplication.util.GeneralUtil;
import com.phan.webtestapplication.util.pagination.PageRequest;
import com.phan.webtestapplication.util.pagination.Pagination;

@Controller
public class ScriptController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ScriptExecutionRepository scriptExecutionRepository;

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(value = "/project/{projectId}/{scriptId}", method = RequestMethod.GET)
    public String openScript(Model model, @PathVariable("projectId") Long projectId,
            @PathVariable("scriptId") Long scriptId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        Script script = scriptRepository.findOne(scriptId);

        model.addAttribute("title", GeneralUtil.getPageTitle(script.getName()));
        model.addAttribute("project", projectRepository.findOne(projectId));
        model.addAttribute("script", scriptRepository.findOne(scriptId));
        model.addAttribute(
                "scriptExecutions",
                scriptExecutionRepository.findByScript(script,
                        new PageRequest(page, Constants.PAGINATION_MAX_PER_PAGE, new Sort(Sort.Direction.DESC, "id")))
                        .getContent());
        model.addAttribute("pagination", new Pagination(page, scriptExecutionRepository.countByScript(script)));

        return "script";
    }

    @RequestMapping(value = "/project/{projectId}/{scriptId}/edit", method = RequestMethod.POST)
    public String updateScript(@ModelAttribute("project") @Valid Script temp, BindingResult errors, Model model,
            @PathVariable("projectId") Long projectId, @PathVariable("scriptId") Long scriptId) {

        Script script = scriptRepository.findOne(scriptId);
        script.setName(temp.getName());
        script.setContent(temp.getContent());
        script.setSchedule(temp.getSchedule());

        model.addAttribute("title", GeneralUtil.getPageTitle(script.getName()));

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            return "script";
        } else {
            scriptRepository.save(script);
            schedulerService.start();

            return "redirect:/project/{projectId}/{scriptId}/";
        }
    }

    @RequestMapping(value = "/project/{projectId}/{scriptId}/execute", method = RequestMethod.GET)
    public String executeScript(Model model, @PathVariable("projectId") Long projectId,
            @PathVariable("scriptId") Long scriptId) throws Exception {

        Long scriptExecutionId = scriptService.execute(scriptId);

        return "redirect:/project/{projectId}/{scriptId}/" + scriptExecutionId;
    }
}
