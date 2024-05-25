package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.MyUser;
import domain.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long>  {

	List<Ticket> findByUserOrderByEventSportNameAscEventDateAsc(MyUser user);

	int findByUserId(Long id);

}
