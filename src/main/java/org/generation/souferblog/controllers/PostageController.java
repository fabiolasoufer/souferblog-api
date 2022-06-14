package org.generation.souferblog.controllers;

import java.util.List;

import org.generation.souferblog.models.Postage;
import org.generation.souferblog.repositories.PostageRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postages")
@CrossOrigin("*")
public class PostageController {
	
	@Autowired
	private PostageRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Postage>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postage> GetById(@PathVariable long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Postage>> GetByTitle(@PathVariable String title) {
		return ResponseEntity.ok(repository.findAllByTitleContainingIgnoreCase(title));
	}
	
	@PostMapping
	public ResponseEntity<Postage> post(@RequestBody Postage postage){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postage));
	}
	
	@PutMapping
	public ResponseEntity<Postage> put(@RequestBody Postage postage) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postage));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
	
	
}
