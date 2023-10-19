package com.generation.farmacia.controller;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByProduto (@PathVariable String nome) {
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainsIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
        if (categoriaRepository.existsById(produto.getCategoria().getId())) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtoRepository.save(produto));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Categoria não existe", null);
        }
    }

    @PutMapping
    public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
        if (produtoRepository.existsById(produto.getId())) {
            if (categoriaRepository.existsById(produto.getCategoria().getId())) {
                return produtoRepository.findById(produto.getId())
                        .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                                .body(produtoRepository.save(produto)))
                        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe", null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        produtoRepository.deleteById(id);
    }


}
