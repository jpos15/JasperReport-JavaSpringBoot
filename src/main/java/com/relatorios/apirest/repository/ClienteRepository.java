package com.relatorios.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.relatorios.apirest.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Cliente findById(long id);
	
}
