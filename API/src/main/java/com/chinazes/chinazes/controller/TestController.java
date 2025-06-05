package com.chinazes.chinazes.controller;

import com.chinazes.chinazes.model.Comms;
import com.chinazes.chinazes.repository.CommsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.Console;
import java.util.List;

@Controller
@RequestMapping("/")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private CommsRepository commsRepository;

    @GetMapping({"/main"})
    public ModelAndView hello(Model model)
    {
        ModelAndView mav = new ModelAndView();
        List<Comms> commsList = commsRepository.findAll();
        log.info("" + commsList.size());
        mav.addObject("comms", commsList);
        mav.setViewName("index");
        return mav;
    }
}
