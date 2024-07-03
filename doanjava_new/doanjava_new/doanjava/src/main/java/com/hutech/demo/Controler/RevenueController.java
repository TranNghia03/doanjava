package com.hutech.demo.Controler;


import org.springframework.ui.Model;
import com.hutech.demo.models.Revenue;
import com.hutech.demo.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/Revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    //@GetMapping("/revenues")
    @GetMapping //("/list")
    public String showAllRevenues(Model model) {
        List<Revenue> revenues; //= revenueService.getAllRevenues();
        model.addAttribute("revenues", revenueService.getAllRevenues());
        return "Revenue/revenues-list"; // This should match the path to your template
    }
}