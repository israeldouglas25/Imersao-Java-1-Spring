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
	public List<Linguagem> list() {
		List<Linguagem> linguagens = repository.findAll();
		return linguagens;
	}
	
	@GetMapping("/ranking")
	public List<Linguagem> listRanking() {
		List<Linguagem> linguagens = repository.findAll();
		linguagens.sort(Comparator.comparing(Linguagem:: getRanking)); // lista por ranking
		return linguagens;
	}

	@PostMapping
	public Linguagem insert(@RequestBody Linguagem linguagem) {
		return repository.save(linguagem);
	}

	@GetMapping("/{id}")
	public Linguagem findById(@PathVariable String id) {
		Optional<Linguagem> linguagem = repository.findById(id);
		return linguagem.get();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Linguagem> update(@PathVariable String id, @RequestBody Linguagem linguagem) {
		Linguagem linguagemDb = findById(id);

		linguagemDb.setTitle(linguagem.getTitle());
		linguagemDb.setImage(linguagem.getImage());
		linguagemDb.setRanking(linguagem.getRanking());

		insert(linguagemDb);

		return ResponseEntity.ok(linguagemDb);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		repository.deleteById(id);
	}

}
