package com.phan.webtestapplication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.phan.webtestapplication.model.Configuration;
import com.phan.webtestapplication.repository.ConfigurationRepository;
import com.phan.webtestapplication.util.GeneralUtil;

@Controller
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping(value = "/configuration", method = RequestMethod.GET)
    public String home(Model model) {

        model.addAttribute("title", GeneralUtil.getPageTitle("Configuration"));
        model.addAttribute("config", configurationRepository.findConfig());

        return "configuration";
    }

    @RequestMapping(value = "/configuration/save", method = RequestMethod.POST)
    public String saveConfiguration(@ModelAttribute("configuration") @Valid Configuration tmp, BindingResult errors,
            Model model) {

        model.addAttribute("title", GeneralUtil.getPageTitle("Configuration"));

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            return "configuration";
        } else {
            Configuration configuration = configurationRepository.findConfig();
            if (configuration == null) {
                configuration = new Configuration();
            }
            configuration.setBinary(tmp.getBinary());
            configuration.setStorage(tmp.getStorage());
            configuration.setProxyHost(tmp.getProxyHost());
            configuration.setProxyPort(tmp.getProxyPort());

            configurationRepository.save(configuration);

            return "redirect:/configuration";
        }
    }

}
