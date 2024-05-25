package com.springBoot_examen;

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
import repository.EventRepository;
import repository.TicketRepository;
import repository.UserRepository;

@Controller
@RequestMapping()
public class TicketController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@GetMapping("/tickets")
	public String showUserTickets(Model model, Authentication authentication) {
	    String username = authentication.getName();
	    MyUser user = userRepository.findByUsername(username);

	    List<Ticket> tickets = ticketRepository.findByUserOrderByEventSportNameAscEventDateAsc(user);
	    model.addAttribute("tickets", tickets);

	    return "userTickets";
	}
	
	@GetMapping("/events/{eventId}/tickets")
	public String showBuyTicketsForm(@PathVariable Long eventId, Model model) {
	    Event event = eventRepository.findById(eventId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid event ID: " + eventId));
	    model.addAttribute("event", event);
	    model.addAttribute("ticket", new Ticket());
	    return "buyTickets";
	}

	@PostMapping("/events/{eventId}/tickets")
	public String buyTickets(@PathVariable Long eventId, @ModelAttribute("ticket") Ticket ticket, BindingResult result, Model model, Authentication authentication) {
	    Event event = eventRepository.findById(eventId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid event ID: " + eventId));
	    
	    // Validate ticket quantity
	    if (ticket.getQuantity() <= 0 || ticket.getQuantity() > 20) {
	        result.rejectValue("quantity", "error.ticket", "Ticket quantity must be between 1 and 20");
	    }
	    
	    // Check available seats
	    if (ticket.getQuantity() > event.getNumberSeats()) {
	        result.rejectValue("quantity", "error.ticket", "Not enough available seats");
	    }
	    
	    if (result.hasErrors()) {
	        model.addAttribute("event", event);
	        return "buyTickets";
	    }
	    
	    // Get the currently logged-in user
	    String username = authentication.getName();
	    MyUser user = userRepository.findByUsername(username);
	   
	    
	    // Create and save the ticket
	    ticket.setEvent(event);
	    ticket.setUser(user);
	    ticketRepository.save(ticket);
	    
	    // Update the available seats for the event
	    event.setNumberSeats(event.getNumberSeats() - ticket.getQuantity());
	    eventRepository.save(event);
	    
	    return "redirect:/sports/" + event.getSport().getId() + "/events";
	}

}