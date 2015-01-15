package com.phan.webtestapplication.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.phan.webtestapplication.model.ReportResult;
import com.phan.webtestapplication.repository.ProjectRepository;
import com.phan.webtestapplication.service.ReportService;
import com.phan.webtestapplication.util.GeneralUtil;

@Controller
public class ReportsController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String openReports(Model model, @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "startdate", required = false) String startdate,
            @RequestParam(value = "enddate", required = false) String enddate) {

        model.addAttribute("title", GeneralUtil.getPageTitle("Report"));

        BindingResult errors = new DirectFieldBindingResult("report", "report");
        List<ReportResult> results = null;

        try {
            results = reportService.getResults(projectId, startdate, enddate);
        } catch (NumberFormatException e) {
            errors.addError(new ObjectError("projectId", "Project ID is wrong"));
        } catch (ParseException e) {
            errors.addError(new ObjectError("projectId", "Date format is wrong"));
        }

        model.addAttribute("projects", projectRepository.findAll());

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
        } else {
            model.addAttribute("results", results);
        }

        return "reports";
    }
}
