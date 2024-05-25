package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.Sport;

@Repository
public interface SportRepository extends CrudRepository<Sport, Long> {

}
