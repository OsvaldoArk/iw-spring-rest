package com.algaworks.rest.repository;

import com.algaworks.rest.model.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long>{
    
}
