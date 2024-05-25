package com.springBoot_examen;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Event;
import domain.Sport;
import repository.EventRepository;
import repository.SportRepository;

@Controller
@RequestMapping("/sports")
public class SportController {
	
	@Autowired
	private SportRepository sportRepository;
	@Autowired
	private EventRepository eventRepository;
	
	@GetMapping()
	public String listSports(Model model) {
		List<Sport> sports = (List<Sport>) sportRepository.findAll();
		model.addAttribute("sports", sports);
		return "sports";
	}
	
	@GetMapping("/{sportId}/events")
	public String listEventsBySport(@PathVariable Long sportId, Model model, @RequestParam(required = false) Integer success) {
	    Sport sport = sportRepository.findById(sportId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid sport ID: " + sportId));
	    List<Event> events = eventRepository.findBySportOrderByDateAsc(sport);
	    model.addAttribute("sport", sport);
	    model.addAttribute("events", events); // Zorg ervoor dat "events" aan het model wordt toegevoegd
	    if (success != null) {
	        model.addAttribute("success", success);
	    }
	    return "events";
	}
	


	

}