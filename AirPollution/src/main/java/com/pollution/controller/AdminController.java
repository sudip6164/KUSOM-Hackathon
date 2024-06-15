package com.pollution.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
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
    public String afeedPage() {
        return "afeed.html";
    }
}
