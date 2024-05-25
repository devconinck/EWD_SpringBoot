package com.springBoot_examen;

import java.util.List;
import java.util.Map;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import domain.Event;
import repository.EventRepository;

@RestController
@RequestMapping(value = "/rest")
public class EventRestController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getEventById(@PathVariable Long id) {
        java.util.Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get().getNumberSeats());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sport/{sportId}")
    public List<Event> getEventsBySportId(@PathVariable Long sportId) {
        return eventRepository.findBySportIdOrderByDateAsc(sportId);
    }
   

}