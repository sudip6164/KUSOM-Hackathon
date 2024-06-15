package com.pollution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pollution.model.Contact;
import com.pollution.repo.ContactRepository;

@Controller
public class ContactController {
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/contact")
	public String contactPage()
	{
		return "contact.html";
	}
	
	@PostMapping("/feedback")
	public String register(@ModelAttribute Contact contact) {
	    
		contactRepository.save(contact);
		return "contact.html";
	}
}
