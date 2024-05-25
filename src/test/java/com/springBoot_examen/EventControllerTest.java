package com.springBoot_examen;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import domain.Event;
import domain.MyUser;
import domain.Sport;
import domain.Stadium;
import repository.EventRepository;
import repository.SportRepository;
import repository.StadiumRepository;
import repository.TicketRepository;
import repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private SportRepository sportRepository;

    @MockBean
    private StadiumRepository stadiumRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowAddEventForm() throws Exception {
        mockMvc.perform(get("/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("addEvent"))
                .andExpect(model().attributeExists("event", "sports", "stadiums"));
    }
    


    
    @Test
    @WithMockUser(roles = "USER")
    public void testShowNewEventForm() throws Exception {
        mockMvc.perform(get("/events/new"))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowNewEventFormForAdmin() throws Exception {
        mockMvc.perform(get("/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("addEvent"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attributeExists("sports"))
                .andExpect(model().attributeExists("stadiums"));
    }
    
    @Test
    @WithAnonymousUser
    public void testAccessSecuredPagesWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/events/1/tickets"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

        mockMvc.perform(get("/events/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
    


	/*  @Test
	@WithMockUser(roles = "ADMIN")
	void testAddEventSuccess() throws Exception {
	    Event event = new Event();
	    event.setSport(new Sport());
	    event.setStadium(new Stadium());
	    event.setDate(LocalDateTime.of(2024, 8, 6, 10, 0));
	    event.setDiscipline1("Discipline 1");
	    event.setOlympicNumber1(12365);
	    event.setOlympicNumber2(13001);
	    event.setTicketPrice(BigDecimal.valueOf(50));
	    event.setNumberSeats(100);
	
	    when(eventRepository.save(any(Event.class))).thenReturn(event);
	
	    mockMvc.perform(post("/events/new")
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .param("sport.id", "1")
	            .param("stadium.id", "1")
	            .param("date", event.getDate().toString())
	            .param("discipline1", event.getDiscipline1())
	            .param("olympicNumber1", String.valueOf(event.getOlympicNumber1()))
	            .param("olympicNumber2", String.valueOf(event.getOlympicNumber2()))
	            .param("ticketPrice", event.getTicketPrice().toString())
	            .param("numberSeats", String.valueOf(event.getNumberSeats())))
	            .andExpect(status().is3xxRedirection())
	            ;
	
	    verify(eventRepository, times(1)).save(any(Event.class));
	}*/
}