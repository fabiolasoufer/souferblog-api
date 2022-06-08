package org.generation.souferblog.repositories;

import java.util.List;

import org.generation.souferblog.models.Postage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostageRepository extends JpaRepository<Postage, Long>{
	
	public List<Postage> findAllByTitleContainingIgnoreCase (String title);

}
