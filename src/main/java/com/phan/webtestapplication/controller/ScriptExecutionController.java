package com.phan.webtestapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.phan.webtestapplication.repository.ProjectRepository;
import com.phan.webtestapplication.repository.ScriptExecutionRepository;
import com.phan.webtestapplication.repository.ScriptRepository;
import com.phan.webtestapplication.util.GeneralUtil;

@Controller
public class ScriptExecutionController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ScriptExecutionRepository scriptExecutionRepository;

    @RequestMapping(value = "/project/{projectId}/{scriptId}/{scriptExecutionId}", method = RequestMethod.GET)
    public String openLog(Model model, @PathVariable("projectId") Long projectId,
            @PathVariable("scriptId") Long scriptId, @PathVariable("scriptExecutionId") Long scriptExecutionId)
            throws Exception {

        model.addAttribute("title", GeneralUtil.getPageTitle("Execution " + scriptExecutionId));
        model.addAttribute("project", projectRepository.findOne(projectId));
        model.addAttribute("script", scriptRepository.findOne(scriptId));
        model.addAttribute("scriptExecution", scriptExecutionRepository.findOne(scriptExecutionId));

        return "scriptExecution";
    }
}
