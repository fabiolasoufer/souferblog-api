package org.generation.souferblog.repositories;

import java.util.List;

import org.generation.souferblog.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
	public List<Theme> findAllByDescriptionContainingIgnoreCase(String description);

}
