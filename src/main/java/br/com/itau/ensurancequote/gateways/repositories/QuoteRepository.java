package br.com.itau.ensurancequote.gateways.repositories;

import br.com.itau.ensurancequote.gateways.repositories.entities.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {}
