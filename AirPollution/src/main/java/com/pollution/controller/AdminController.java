package com.pollution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pollution.repo.ContactRepository;

@Controller
public class AdminController {
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/admin")
	public String adminPage()
	{
		return "admindash.html";
	}
	@GetMapping("/admin/amap")
    public String amapPage() {
        return "amap.html";
    }
	@GetMapping("/admin/asensor")
    public String asensorPage() {
        return "asensor.html";
    }
	@GetMapping("/admin/hsensor")
    public String hsensorPage() {
        return "hsensor.html";
    }
	@GetMapping("/admin/tsensor")
    public String tsensorPage() {
        return "tsensor.html";
    }
	 @GetMapping("/admin/afeed")
	    public String afeedPage(Model model) {
	        model.addAttribute("contact", contactRepository.findAll());
	        return "afeed.html";
	    }
}
