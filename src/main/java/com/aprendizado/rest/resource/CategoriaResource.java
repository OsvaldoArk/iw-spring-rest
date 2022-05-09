package com.algaworks.rest.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import com.algaworks.rest.model.Categoria;
import com.algaworks.rest.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
    @Autowired
    private CategoriaRepository categoriaRepository;
  
    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    };

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscategoria(@PathVariable Long codigo){
        
        Optional<Categoria> resultado = categoriaRepository.findById(codigo);

        return resultado.isPresent()?
        ResponseEntity.ok(resultado.get()): ResponseEntity.notFound().build();

        /*
            fazendo com map
            return categoriaRepository.findById(codigo)
                .map(resultado -> ResponseEntity.ok(resultado))
                .orElse(ResponseEntity.notFound().build());
        */

    };

    /* outra maneira

    @GetMapping
    public ResponseEntity<?> listar(){
        
        List<Categoria> categorias =  categoriaRepository.findAll();

        return !categorias.isEmpty()? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
    };
    
    */


    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse resposta){

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
        .buildAndExpand(categoriaSalva.getCodigo()).toUri();
        
        resposta.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categoriaSalva);
    }
}
