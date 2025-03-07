package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.MyUser;

@Repository
public interface UserRepository extends CrudRepository<MyUser, Long> {

	MyUser findByUsername(String username);

}
