package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.Event;
import domain.Sport;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findBySportOrderByDateAsc(Sport sport);

	Event findByOlympicNumber1(int olympicNumber1);

	List<Event> findBySportIdOrderByDateAsc(Long sportId);

}
