package org.generation.souferblog.repositories;

import java.util.Optional;

import org.generation.souferblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByUser(String user);

}
