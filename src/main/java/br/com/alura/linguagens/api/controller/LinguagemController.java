package br.com.alura.linguagens.api.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.linguagens.api.model.Linguagem;
import br.com.alura.linguagens.api.repository.LinguagemRepository;

@RestController
@RequestMapping("linguagens")
public class LinguagemController {

	@Autowired
	private LinguagemRepository repository;

	@GetMapping
	public ResponseEntity<List<Linguagem>> list() {
		List<Linguagem> linguagens = repository.findAll();
		linguagens.sort(Comparator.comparing(Linguagem:: getRanking)); // lista por ranking
		return ResponseEntity.ok(linguagens);
	}

	@PostMapping
	public ResponseEntity<Linguagem> insert(@RequestBody Linguagem linguagem) {
		repository.save(linguagem);
		return ResponseEntity.ok(linguagem) ;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Linguagem> findById(@PathVariable String id) {
		Optional<Linguagem> linguagem = repository.findById(id);
		return ResponseEntity.ok(linguagem.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Linguagem> update(@PathVariable String id, @RequestBody Linguagem linguagem) {
		Optional<Linguagem> linguagemDb = repository.findById(id);

		linguagemDb.get().setTitle(linguagem.getTitle());
		linguagemDb.get().setImage(linguagem.getImage());
		linguagemDb.get().setRanking(linguagem.getRanking());

		repository.save(linguagemDb.get());

		return ResponseEntity.ok(linguagemDb.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Linguagem> delete(@PathVariable String id) {
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
