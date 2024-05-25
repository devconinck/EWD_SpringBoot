package com.springBoot_examen;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Event;
import domain.MyUser;
import domain.Ticket;
import jakarta.validation.Valid;
import repository.EventRepository;
import repository.SportRepository;
import repository.StadiumRepository;
import repository.TicketRepository;
import repository.UserRepository;


@Controller
@RequestMapping()
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private SportRepository sportRepository;
	
	@Autowired
	private StadiumRepository stadiumRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/events/new")
	public String showAddEventForm(Model model) {
	    model.addAttribute("event", new Event());
	    model.addAttribute("sports", sportRepository.findAll());
	    model.addAttribute("stadiums", stadiumRepository.findAll());
	    return "addEvent";
	}

	@PostMapping("/events/new")
	public String addEvent( @Valid @ModelAttribute("event") Event event, BindingResult result, Model model) {
		 // Custom validation for Olympic Number 2
	    if (event.getOlympicNumber2() < event.getOlympicNumber1() - 1000
	            || event.getOlympicNumber2() > event.getOlympicNumber1() + 1000) {
	        result.rejectValue("olympicNumber2", "error.event",
	                "Olympic Number 2 must be within a range of 1000 from Olympic Number 1");
	    }

	    // Custom validation for disciplines
	    if (event.getDiscipline1() != null && event.getDiscipline1().equals(event.getDiscipline2())) {
	        result.rejectValue("discipline2", "error.event", "Disciplines must be different");
	    }

	    if (result.hasErrors()) {
	        model.addAttribute("sports", sportRepository.findAll());
	        model.addAttribute("stadiums", stadiumRepository.findAll());
	        return "addEvent";
	    }

	    eventRepository.save(event);
	    return "redirect:/sports/" + event.getSport().getId() + "/events";
	}

	


}