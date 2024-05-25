package com.springBoot_examen;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;


import domain.Event;
import domain.MyUser;
import domain.Role;
import domain.Sport;
import domain.Stadium;
import domain.Ticket;
import repository.EventRepository;
import repository.SportRepository;
import repository.StadiumRepository;
import repository.TicketRepository;
import repository.UserRepository;


@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private StadiumRepository stadiumRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private SportRepository sportRepository;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	private static final String BCRYPTED_PASWOORD = "$2a$12$Vy.MTwPGc0CkwGOLeZ5s1eLl0nWXyL0p.L0UD3SQ99Uuug6YJq3SO";
	// string 'root'

	@Override
	public void run(String... args) throws Exception {

		final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		var admin = MyUser.builder().username("admin").role(Role.ADMIN).password(BCRYPTED_PASWOORD).build();
		var user = MyUser.builder().username("user").role(Role.USER).password(BCRYPTED_PASWOORD).build();
		var test = MyUser.builder().username("test").role(Role.USER).password(BCRYPTED_PASWOORD).build();

		userRepository.save(user);
		userRepository.save(admin);
		userRepository.save(test);

		Sport sport1 = new Sport("Cycling");
		Sport sport2 = new Sport("Basketball");
		Sport sport3 = new Sport("Athletics");
		Sport sport4 = new Sport("Swimming");
		Sport sport5 = new Sport("Weightlifting");
		Sport sport6 = new Sport("Football");

		sportRepository.saveAll(Arrays.asList(sport1, sport2, sport3, sport4, sport5, sport6));

		Stadium stadium1 = new Stadium("Stade de France");
		Stadium stadium2 = new Stadium("Pierre Mauroy Stadium");
		Stadium stadium3 = new Stadium("Parc des Princes");
		Stadium stadium4 = new Stadium("Accor Arena");
		Stadium stadium5 = new Stadium("Stade Jean-Bouin");

		stadiumRepository.saveAll(Arrays.asList(stadium1, stadium2, stadium3, stadium4, stadium5));

		 Event event1 = new Event(sport1, stadium1, LocalDateTime.of(2024, 7, 26, 10, 0), "Men's Road Race", null,
				 12345, 13300, BigDecimal.valueOf(75.00), 49);

		 Event event2 = new Event(sport2, stadium2, LocalDateTime.of(2024, 7, 28, 14, 0), "Women's Basketball", null,
				 23456, 22500, BigDecimal.valueOf(50.00), 20);

		 Event event3 = new Event(sport3, stadium3, LocalDateTime.of(2024, 8, 1, 9, 0), "Men's 100m", "Women's 400m",
				 34567, 35500, BigDecimal.valueOf(100.00), 40);
		 Event event4 = new Event(sport4, stadium4, LocalDateTime.of(2024, 8, 5, 16, 0), "Men's 200m Freestyle", null,
				 45678, 46600, BigDecimal.valueOf(80.00), 10);
		 Event event5 = new Event(sport5, stadium5, LocalDateTime.of(2024, 8, 10, 11, 0), "Women's 49kg", "Men's 61kg",
				 56789, 57700, BigDecimal.valueOf(20.00),5);
		 Event event6 = new Event(sport6, stadium1, LocalDateTime.of(2024, 8, 1, 11, 0), "Women's Basketball", null,
				 56781, 57706, BigDecimal.valueOf(140.00),12);
		 Event event7 = new Event(sport2, stadium1, LocalDateTime.of(2024, 8, 3, 12, 0), "Men's Basketball", null,
				 56782, 57704, BigDecimal.valueOf(5.00),9);
		 Event event8 = new Event(sport2, stadium1, LocalDateTime.of(2024, 8, 4, 18, 0), "3v3", null,
				 56783, 57702, BigDecimal.valueOf(10.00),1);
		 Event event9 = new Event(sport2, stadium1, LocalDateTime.of(2024, 8, 6, 13, 0), "Men's Basketball 3v3", null,
				 56784, 57701, BigDecimal.valueOf(120.00),44);


	     eventRepository.saveAll(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
	     

	}

}