package org.generation.souferblog.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.souferblog.models.Postage;
import org.generation.souferblog.repositories.PostageRepository;
import org.generation.souferblog.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/postages")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostageController {
	
	@Autowired
	private PostageRepository postageRepository;
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@GetMapping
	public ResponseEntity<List<Postage>> getAll() {
		return ResponseEntity.ok(postageRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postage> getById(@PathVariable long id) {
		return postageRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Postage>> getByTitle(@PathVariable String title) {
		return ResponseEntity.ok(postageRepository.findAllByTitleContainingIgnoreCase(title));
	}
	
	@PostMapping
	public ResponseEntity<Postage> post(@Valid @RequestBody Postage postage){
		if (themeRepository.existsById(postage.getTheme().getId()))
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postageRepository.save(postage));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PutMapping
	public ResponseEntity<Postage> put(@Valid @RequestBody Postage postage) {
		if (postageRepository.existsById(postage.getId())) {
			
			if (themeRepository.existsById(postage.getTheme().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(postageRepository.save(postage));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		Optional<Postage> postage = postageRepository.findById(id);
		
		if(postage.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postageRepository.deleteById(id);
	}
	
	
}
