package com.springBoot_examen;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import domain.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EventRestClient {

    private final WebClient webClient;

    public EventRestClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<Integer> getAvailableSeats(Long eventId) {
        return webClient.get()
                .uri("/events/{id}/seats", eventId)
                .retrieve()
                .bodyToMono(Integer.class);
    }

    public Flux<Event> getEventsBySportId(Long sportId) {
        return webClient.get()
                .uri("/events/sport/{sportId}", sportId)
                .retrieve()
                .bodyToFlux(Event.class);
    }
}